package com.student.scheduleservice.internal.provider;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.dal.UnavailabilityDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.SoapAddUnavailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarUnavailabilitiesRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapUnavailability;
import com.student.soap.contract.scheduleservice.SoapUnavailabilityResponse;

@Component("UnavailabilityProvider")
public class UnavailabilityProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public UnavailabilityProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	private boolean authanticated(AuthenticationTokenParseResult token, Permission permission) {

		if (!token.isValid() || !token.getRoleName().equals("AGENT")) {
			return false;
		}

		if (permission == null) {
			return false;
		}

		return true;
	}

	public SoapUnavailabilityResponse getCarUnavailability (SoapCarUnavailabilitiesRequest request) {
		SoapUnavailabilityResponse response = new SoapUnavailabilityResponse();
		
		List<UnavailabilityDbModel> unavailabilities = unitOfWork.getUnavailabilityRepo().findByCarId(request.getCarId());
		if(request.getStartDate()!= null) {
			unavailabilities = unavailabilities.stream().filter(x-> x.getStartDate().after(request.getStartDate().toGregorianCalendar().getTime()))
					.collect(Collectors.toList());;
		}
		
		if(request.getEndDate()!= null) {
			unavailabilities = unavailabilities.stream().filter(x-> x.getStartDate().before(request.getEndDate().toGregorianCalendar().getTime()))
					.collect(Collectors.toList());;
		}
		
		for(UnavailabilityDbModel objIn:  unavailabilities) {
			SoapUnavailability objOut = new SoapUnavailability();
			objOut.setId(objIn.getId());
			objOut.setStartDate(providerUtil.getXmlGregorianCalendar(objIn.getStartDate()));
			objOut.setEndDate(providerUtil.getXmlGregorianCalendar(objIn.getEndDate()));
			
			response.getUnavailability().add(objOut);
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse addUnavailability(SoapAddUnavailabilityRequest request) {

		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream().filter(p -> p.getPermissionId() == 3).findFirst()
				.orElse(null);

		if (!authanticated(token, permission)) {
			response.setAuthorized(false);
			return response;
		}

		Date requestedStartTime = request.getStartDate().toGregorianCalendar().getTime();
		Date requestedtEndTime = request.getEndDate().toGregorianCalendar().getTime();

		// Are there any paid reservations?
		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(request.getCarId()).stream()
				.filter(x -> providerUtil.datesOverlap(x.getStartDate(), x.getEndDate(), requestedStartTime,
						requestedtEndTime))
				.filter(reservation -> reservation.getBundle().getState().getId() == providerUtil.getPaidState()
						.getId())
				.collect(Collectors.toList());

		if (reservations.size() > 0) {
			return response;
		}

		// save
		UnavailabilityDbModel unavailability = new UnavailabilityDbModel();
		unavailability.setCarId(request.getCarId());
		unavailability.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		unavailability.setEndDate(request.getEndDate().toGregorianCalendar().getTime());

		unitOfWork.getUnavailabilityRepo().save(unavailability);

		// Odbijanje ostalih nepotvrdjenih zahteva
		List<BundleDbModel> rejectedBundles = unitOfWork.getBundleRepo().findByStateIdAndPublisherIdAndPublisherTypeId(
				providerUtil.getPendingState().getId(), permission.getResourceId(), permission.getResourceTypeId());

		for (BundleDbModel rejectedBundle : rejectedBundles) {

			// Is there a reservation that overlaps with approved reservations
			for (ReservationDbModel rejectedReservation : rejectedBundle.getReservations()) {

				// is it the same car?
				if (request.getCarId() != rejectedReservation.getCarId()) {
					continue;
				}

				Date start1 = requestedStartTime;
				Date end1 = requestedtEndTime;
				Date start2 = rejectedReservation.getStartDate();
				Date end2 = rejectedReservation.getEndDate();

				// are the dates overlapping?
				if (providerUtil.datesOverlap(start1, end1, start2, end2)) {
					rejectedBundle.setState(providerUtil.getRejectedState());
					unitOfWork.getBundleRepo().save(rejectedBundle);
				}
			}
		}

		response.setSuccess(true);
		return response;
	}
}
