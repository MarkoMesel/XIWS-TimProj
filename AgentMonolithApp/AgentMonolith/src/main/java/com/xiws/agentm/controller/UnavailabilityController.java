package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//import com.student.http.contract.HttpUnavailabilityRequest;
//import com.student.http.contract.HttpUnavailabilityResponse;
//import com.student.internal.translator.Translator;
//import com.student.scheduleservice.data.dal.BundleDbModel;
//import com.student.scheduleservice.data.dal.ReservationDbModel;
//import com.student.scheduleservice.data.dal.UnavailabilityDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.scheduleservice.jwt.Permission;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.SoapAddUnavailabilityRequest;
//import com.student.soap.contract.scheduleservice.SoapCarUnavailabilitiesRequest;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.soap.contract.scheduleservice.SoapUnavailabilityResponse;
import com.xiws.agentm.request.UnavailabilityRequestModel;
import com.xiws.agentm.response.UnavailabilityResponseModel;
import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationStateDbModel;
import com.xiws.agentm.scheduleservice.data.dal.UnavailabilityDbModel;
import com.xiws.agentm.scheduleservice.data.repo.BundleRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationStateRepo;
import com.xiws.agentm.scheduleservice.data.repo.UnavailabilityRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.soap.contract.scheduleservice.SoapUnavailability;
//import com.student.soap.contract.scheduleservice.SoapUnavailabilityResponse;
import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;

@Controller
public class UnavailabilityController {
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	ReservationStateRepo reservationStateRepo;
	
	@Autowired
	UnavailabilityRepo unavailabilityRepo;
	
	@Autowired
	BundleRepo bundleRepo;
	
//	private ScheduleServiceClient scheduleServiceClient;
//	private Translator translator;
//
//	@Autowired
//	public UnavailabilityController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
//		this.scheduleServiceClient = scheduleServiceClient;
//		this.translator = translator;
//	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/car/unavailable")
	public ResponseEntity<?> addCarUnavailability(@RequestHeader("token") String token,
			@RequestBody UnavailabilityRequestModel request) {
//		SoapAddUnavailabilityRequest internalRequest = new SoapAddUnavailabilityRequest();
//
//		internalRequest.setToken(token);
//		internalRequest.setCarId(request.getCarId());
//		internalRequest.setStartDate(request.getStartDate());
//		internalRequest.setEndDate(request.getEndDate());
//
//		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);


//		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 3).findFirst()
				.orElse(null);

		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Date requestedStartTime = request.getStartDate().toGregorianCalendar().getTime();
		Date requestedtEndTime = request.getEndDate().toGregorianCalendar().getTime();

		// Are there any paid reservations?
		List<ReservationDbModel> reservations = reservationRepo.findByCarId(request.getCarId()).stream()
				.filter(x -> datesOverlap(x.getStartDate(), x.getEndDate(), requestedStartTime,
						requestedtEndTime))
				.filter(reservation -> reservation.getBundle().getState().getId() == getPaidState()
						.getId())
				.collect(Collectors.toList());

		if (reservations.size() > 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		// save
		UnavailabilityDbModel unavailability = new UnavailabilityDbModel();
		unavailability.setCarId(request.getCarId());
		unavailability.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		unavailability.setEndDate(request.getEndDate().toGregorianCalendar().getTime());

		unavailabilityRepo.save(unavailability);

		// Odbijanje ostalih nepotvrdjenih zahteva
		List<BundleDbModel> rejectedBundles = bundleRepo.findByStateIdAndPublisherIdAndPublisherTypeId(
				getPendingState().getId(), permission.getResourceId(), permission.getResourceTypeId());

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
				if (datesOverlap(start1, end1, start2, end2)) {
					rejectedBundle.setState(getRejectedState());
					bundleRepo.save(rejectedBundle);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
//		if (!internalResponse.isSuccess()) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/car/unavailabilities")
	public ResponseEntity<List<UnavailabilityResponseModel>> getCarUnavailability(@RequestBody UnavailabilityRequestModel request) {
//		SoapCarUnavailabilitiesRequest internalRequest = new SoapCarUnavailabilitiesRequest();
//
//		internalRequest.setCarId(request.getCarId());
//		internalRequest.setStartDate(request.getStartDate());
//		internalRequest.setEndDate(request.getEndDate());
//
//		SoapUnavailabilityResponse internalResponse = scheduleServiceClient.send(internalRequest);

//		SoapUnavailabilityResponse response = new SoapUnavailabilityResponse();
		
		List<UnavailabilityResponseModel> internalResponse = new ArrayList<UnavailabilityResponseModel>();
		
		List<UnavailabilityDbModel> unavailabilities = unavailabilityRepo.findByCarId(request.getCarId());
		if(request.getStartDate()!= null) {
			unavailabilities = unavailabilities.stream().filter(x-> x.getStartDate().after(request.getStartDate().toGregorianCalendar().getTime()))
					.collect(Collectors.toList());;
		}
		
		if(request.getEndDate()!= null) {
			unavailabilities = unavailabilities.stream().filter(x-> x.getStartDate().before(request.getEndDate().toGregorianCalendar().getTime()))
					.collect(Collectors.toList());;
		}
		
		for(UnavailabilityDbModel objIn:  unavailabilities) {
			UnavailabilityResponseModel objOut = new UnavailabilityResponseModel();
			objOut.setId(objIn.getId());
			objOut.setStartDate(getXmlGregorianCalendar(objIn.getStartDate()));
			objOut.setEndDate(getXmlGregorianCalendar(objIn.getEndDate()));
			
			internalResponse.add(objOut);
		}
		
		return new ResponseEntity<List<UnavailabilityResponseModel>>(internalResponse , HttpStatus.OK);
		
//		if (!internalResponse.isSuccess()) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<List<HttpUnavailabilityResponse>>(translator.translate(internalResponse) , HttpStatus.OK);
	}
	
	public AuthenticationTokenParseResult parseAuthenticationToken(String token) {
		AuthenticationTokenParseResult result = new AuthenticationTokenParseResult();
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));
			Object permissionsObject = claims.get("permissions");
			ObjectMapper mapper = new ObjectMapper();
			List<Permission> permissions= mapper.convertValue(permissionsObject, new TypeReference<List<Permission>>() { });
			result.setPermissions(permissions);
			result.setRoleName((String) claims.get("roleName"));
			result.setRoleId((Integer) claims.get("roleId"));

			if (result.getIssuer() == null || !result.getIssuer().equals(issuer) || result.getUserId() == null
					|| result.getPurpose() == null || !result.getPurpose().equals(AUTHENTICATION_STRING)
					|| (!(result.getRoleName().equals("BASIC") && result.getRoleId() == 1)
							&& !(result.getRoleName().equals("AGENT") && result.getRoleId() == 2)
							&& !(result.getRoleName().equals("ADMIN") && result.getRoleId() == 3))) {
				result = new AuthenticationTokenParseResult();
				result.setValid(false);
				return result;
			}
			result.setValid(true);
			return result;
		} catch (Exception e) {
			result = new AuthenticationTokenParseResult();
			result.setValid(false);
			return result;
		}
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
	
	public boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
		return !start1.after(end2) && !start2.after(end1);
	}
	
	public ReservationStateDbModel getPaidState() {
		return reservationStateRepo.findById(4).get();
	}
	
	public ReservationStateDbModel getPendingState() {
		return reservationStateRepo.findById(2).get();
	}
	
	public ReservationStateDbModel getRejectedState() {
		return reservationStateRepo.findById(5).get();
	}
	
	public XMLGregorianCalendar getXmlGregorianCalendar(Date date) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
