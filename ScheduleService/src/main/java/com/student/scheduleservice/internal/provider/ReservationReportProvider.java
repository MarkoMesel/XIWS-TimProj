package com.student.scheduleservice.internal.provider;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.SoapAddReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapPendingReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("ReservationReportProvider")
public class ReservationReportProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public ReservationReportProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	private boolean authanticated(AuthenticationTokenParseResult token, ReservationDbModel reservation, Permission permission) {
		
		if(!token.isValid() || (!token.getRoleName().equals("BASIC") && !token.getRoleName().equals("AGENT"))) {
			return false;
		}
		
		if( permission == null) {
			return false;
		}
		
		if(reservation == null) {
			return true;
		}
		
		return permission.getResourceId()==reservation.getBundle().getPublisherId() && permission.getResourceTypeId()==reservation.getBundle().getPublisherType().getId();
	}

	public SoapResponse addReport(SoapAddReservationReportRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<ReservationDbModel> reservation = unitOfWork.getReservationRepo().findById(request.getReservationId());

		// Does the reservation exist?
		if (!reservation.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		
		Permission permission = token.getPermissions().stream()
				.filter(p -> p.getPermissionId() == 7 ).findFirst().orElse(null);

		if (!authanticated(token, reservation.get(), permission)) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		
		//Is the reservation complete?
		if (reservation.get().getBundle().getState().getId() != providerUtil.getCompletedState().getId()) {
			response.setSuccess(false);
			return response;
		}
		//Is there already a report?
		if (reservation.get().getReportedMileage() != null) {
			response.setSuccess(false);
			return response;
		}
		
		reservation.get().setReportedMileage(request.getMileage());

		if(request.getComment() == null || request.getComment().trim().length() == 0) {
			reservation.get().setReportedComment(request.getComment());
		}

		unitOfWork.getReservationRepo().save(reservation.get());
		response.setSuccess(true);
		return response;
	}

	public SoapReservationsResponse getReservations(SoapPendingReservationReportRequest request) {
		SoapReservationsResponse response = new SoapReservationsResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream()
				.filter(p -> p.getPermissionId() == 7 ).findFirst().orElse(null);
		
		if (!authanticated(token, null, permission)) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo()
				.findByStateIdAndPublisherIdAndPublisherTypeId(providerUtil.getCompletedState().getId(), permission.getResourceId(), permission.getResourceTypeId());
		
		for(BundleDbModel bundleIn : bundles) {
			for(ReservationDbModel reservationIn : bundleIn.getReservations()) {
				//Vec upisan report?
				if(reservationIn.getReportedMileage() != null) {
					continue;
				}

				response.getReservation().add(providerUtil.getSoapReservations(reservationIn));
				
			}
		}
		response.setSuccess(true);
		return response;
	}
}
