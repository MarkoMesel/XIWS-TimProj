package com.student.scheduleservice.internal.provider;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
import com.student.soap.contract.scheduleservice.SoapPayReservationRequest;
import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsRequest;
import com.student.soap.contract.scheduleservice.SoapReserveRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("ReservationProvider")
public class ReservationProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;
	private ProviderUtil providerUtil;

	@Autowired
	public ReservationProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	public SoapResponse reserve(SoapReserveRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), providerUtil.getCartState().getId());
		
		for(BundleDbModel bundle: bundles) {
			bundle.setState(providerUtil.getPendingState());
			unitOfWork.getBundleRepo().save(bundle);
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse approve(SoapApproveReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse reject(SoapRejectReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse cancel(SoapCancelReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse pay(SoapPayReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapBundlesResponse getBundles(SoapReservationsRequest request) {
		SoapBundlesResponse response = new SoapBundlesResponse();

		response.setSuccess(true);
		return response;
	}
	
	@Scheduled(fixedRate = 2000)
	public void switchToComplete() {
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByStateId(providerUtil.getReservedState().getId());

		GregorianCalendar currentDateTime = new GregorianCalendar();
		
		for(BundleDbModel bundle : bundles) {
			for(ReservationDbModel reservation : bundle.getReservations()) {
				//Da li ima nezavrsenih rezervacija
				if(reservation.getEndDate().after(currentDateTime.getTime())) {
					//preskoci budle
					break;
				}
				
				bundle.setState(providerUtil.getCompletedState());
				unitOfWork.getBundleRepo().save(bundle);
			}
		}
	}
}
