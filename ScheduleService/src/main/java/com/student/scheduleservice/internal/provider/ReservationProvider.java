package com.student.scheduleservice.internal.provider;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
import com.student.soap.contract.scheduleservice.SoapPublisherReservationsRequest;
import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
import com.student.soap.contract.scheduleservice.SoapReserveRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapUserReservationsRequest;

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
	
	private boolean authanticated(AuthenticationTokenParseResult token, Permission permission) {
		
		//Da li je validan token? Da li postoji potrebna permisija u tokenu?
		if (!token.isValid() || permission == null)
		{			
			return false;
		}
		
		boolean hasUserPermission = token.getRoleName().equals("BASIC") && permission.getResourceId() == token.getUserId() && permission.getResourceTypeId() == 1;
		boolean hasPublisherPermission = token.getRoleName().equals("AGENT") && permission.getResourceId()!=null && permission.getResourceTypeId() == 2;
		
		//Da li je korisnik ili agent?
		if( !hasUserPermission && !hasPublisherPermission ) {
			return false;
		}
		
		return true;
	}

	public SoapResponse reserve(SoapReserveRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(),
				providerUtil.getCartState().getId());

		for (BundleDbModel bundle : bundles) {
			bundle.setState(providerUtil.getPendingState());
			unitOfWork.getBundleRepo().save(bundle);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapResponse approve(SoapApproveReservationRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = unitOfWork.getBundleRepo().findById(request.getBundleId());

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != providerUtil.getPendingState().getId()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAuthorized(token, 5, bundle.get().getPublisherId(), bundle.get().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		bundle.get().setState(providerUtil.getPaidState());
		unitOfWork.getBundleRepo().save(bundle.get());

		List<BundleDbModel> rejectedBundles = unitOfWork.getBundleRepo()
				.findByStateIdAndPublisherIdAndPublisherTypeId(providerUtil.getPendingState().getId(), bundle.get().getPublisherId(), bundle.get().getPublisherType().getId());
		
		for(BundleDbModel rejectedBundle : rejectedBundles) {
			
			//Is there a reservation that overlaps with approved reservations
			for(ReservationDbModel rejectedReservation : rejectedBundle.getReservations()) {
				for( ReservationDbModel approvedReservation : bundle.get().getReservations()) {
					
					//is it the same car?
					if(approvedReservation.getCarId() != rejectedReservation.getCarId()) {
						continue;
					}
					
					Date start1 = approvedReservation.getStartDate();
					Date end1 = approvedReservation.getEndDate();
					Date start2 = rejectedReservation.getStartDate();
					Date end2 = rejectedReservation.getEndDate();
					
					//are the dates overlapping?
					if(!start1.after(end2) && !start2.after(end1)) {
						rejectedBundle.setState(providerUtil.getRejectedState());
						unitOfWork.getBundleRepo().save(rejectedBundle);
					}
				}
			}
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse reject(SoapRejectReservationRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = unitOfWork.getBundleRepo().findById(request.getBundleId());

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != providerUtil.getPendingState().getId()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAuthorized(token, 5, bundle.get().getPublisherId(), bundle.get().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		bundle.get().setState(providerUtil.getRejectedState());
		unitOfWork.getBundleRepo().save(bundle.get());

		response.setSuccess(true);
		return response;
	}

	public SoapResponse cancel(SoapCancelReservationRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = unitOfWork.getBundleRepo().findById(request.getBundleId());

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != providerUtil.getPendingState().getId()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		//Da li bundle pripada ovom korisniku?
		if (!token.isValid() || token.getUserId() != bundle.get().getUserId()) {
			response.setAuthorized(false);
			return response;
		}

		bundle.get().setState(providerUtil.getCanceledState());
		unitOfWork.getBundleRepo().save(bundle.get());
		
		response.setSuccess(true);
		return response;
	}

	public SoapBundlesResponse getUserBundles(SoapUserReservationsRequest request) {
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			SoapBundlesResponse response = new SoapBundlesResponse();
			response.setAuthorized(false);
			return response;
		}

		// find all bundles not in cart
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserId(token.getUserId()).stream()
				.filter(bundle -> bundle.getState().getId() != providerUtil.getCartState().getId())
				.collect(Collectors.toList());

		SoapBundlesResponse response = providerUtil.getSoapBundles(bundles);

		return response;
	}

	public SoapBundlesResponse getPublisherBundles(SoapPublisherReservationsRequest request) {
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == 5 ).findFirst().orElse(null);
		
		if(!authanticated(token, requiredPermission)){
			SoapBundlesResponse response = new SoapBundlesResponse();
			response.setAuthorized(false);
			return response;
		}
		
		// find all bundles that are not in cart
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo()
				.findByPublisherIdAndPublisherTypeId(requiredPermission.getResourceId(),  token.getRoleId())
				.stream()
				.filter(bundle -> bundle.getState().getId() != providerUtil.getCartState().getId())
				.collect(Collectors.toList());

		SoapBundlesResponse response = providerUtil.getSoapBundles(bundles);

		return response;
	}

	@Scheduled(fixedRate = 2000) //Svake 2 sekunde
	public void switchToComplete() {
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByStateId(providerUtil.getPaidState().getId());

		GregorianCalendar currentDateTime = new GregorianCalendar();

		for (BundleDbModel bundle : bundles) {
			for (ReservationDbModel reservation : bundle.getReservations()) {
				// Da li ima nezavrsenih rezervacija
				if (reservation.getEndDate().after(currentDateTime.getTime())) {
					// preskoci budle
					break;
				}

				bundle.setState(providerUtil.getCompletedState());
				unitOfWork.getBundleRepo().save(bundle);
			}
		}
	}
}
