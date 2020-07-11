package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.soap.contract.carservice.SoapCarRequest;
//import com.student.soap.contract.carservice.SoapCarResponse;
//import com.student.soap.contract.scheduleservice.Reservation;
//import com.student.soap.contract.scheduleservice.SoapResponse;

//import com.student.scheduleservice.data.dal.BundleDbModel;
//import com.student.scheduleservice.data.dal.ReservationDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.scheduleservice.jwt.Permission;
//import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
//import com.xiws.agentm.response.ReservationResponseModel;

import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;
import com.xiws.agentm.agentservice.data.dal.AgentDbModel;
import com.xiws.agentm.agentservice.data.repo.AgentRepo;
import com.xiws.agentm.carservice.data.dal.CarDbModel;
import com.xiws.agentm.carservice.data.dal.CarImageDbModel;
import com.xiws.agentm.carservice.data.repo.CarRepo;
import com.xiws.agentm.request.AddReservationReportRequestModel;
import com.xiws.agentm.response.ReservationResponseModel;
import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;
import com.xiws.agentm.scheduleservice.data.repo.BundleRepo;
import com.xiws.agentm.scheduleservice.data.repo.CarPriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationStateRepo;
import com.xiws.agentm.userservice.data.dal.UserDbModel;
import com.xiws.agentm.userservice.data.repo.UserRepo;
import com.xiws.agentm.scheduleservice.data.dal.ReservationStateDbModel;
import com.xiws.agentm.scheduleservice.data.dal.CarPriceListDbModel;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

//import com.student.http.contract.HttpAddReservationReportRequest;
//import com.student.http.contract.HttpReservationResponse;
//import com.student.internal.translator.Translator;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.SoapAddReservationReportRequest;
//import com.student.soap.contract.scheduleservice.SoapPendingReservationReportRequest;
//import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
//import com.student.soap.contract.scheduleservice.SoapResponse;

@Controller
public class ReservationReportController {
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	BundleRepo bundleRepo;
	
	@Autowired
	ReservationStateRepo reservationStateRepo;
	
	@Autowired
	CarPriceListRepo carPriceListRepo;
	
	@Autowired
	CarRepo carRepo;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AgentRepo agentRepo;
	
	/*
	 * // private Translator translator; // private ScheduleServiceClient
	 * scheduleServiceClient; // // @Autowired // public
	 * ReservationReportController(ScheduleServiceClient scheduleServiceClient,
	 * Translator translator) { // this.scheduleServiceClient =
	 * scheduleServiceClient; // this.translator = translator; // }
	 */	
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/report/pending")
	public ResponseEntity<List<ReservationResponseModel>> getMessages(@RequestHeader("token") String token) {
		
		/*
		 * SoapPendingReservationReportRequest internalRequest = new
		 * SoapPendingReservationReportRequest(); internalRequest.setToken(token);
		 * 
		 * SoapReservationsResponse internalResponse =
		 * scheduleServiceClient.send(internalRequest);
		 */
		
		//SoapReservationsResponse response = new SoapReservationsResponse();
		
		List<ReservationResponseModel> internalResponse = new ArrayList<ReservationResponseModel>();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream()
				.filter(p -> p.getPermissionId() == 7 ).findFirst().orElse(null);
		
		if (!authanticated(tokenObj, null, permission)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		//response.setAuthorized(true);
		
		List<BundleDbModel> bundles = bundleRepo
				.findByStateIdAndPublisherIdAndPublisherTypeId(getCompletedState().getId(), permission.getResourceId(), permission.getResourceTypeId());
		
		for(BundleDbModel bundleIn : bundles) {
			for(ReservationDbModel reservationIn : bundleIn.getReservations()) {
				//Vec upisan report?
				if(reservationIn.getReportedMileage() != null) {
					continue;
				}
				
				ReservationResponseModel reservationOut = new ReservationResponseModel();

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut
						.setExtraCharges(reservationIn.getExtraCharges() == null ? 0 : reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = carPriceListRepo
						.findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp()))
						.findFirst().orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}

				// fetch car
				//SoapCarRequest soapCarRequest = new SoapCarRequest();
				//soapCarRequest.setId(reservationIn.getCarId());
				Optional<CarDbModel> carOptional = carRepo.findById(reservationIn.getCarId());
				if (!carOptional.isPresent() || !carOptional.get().isActive()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				CarDbModel car = carOptional.get();

				//if (soapCarResponse.isSuccess()) {
				reservationOut.setCarClassId(car.getCarClass().getId());
				reservationOut.setCarClassName(car.getCarClass().getName());
				reservationOut.setLocationId(car.getLocation().getId());
				reservationOut.setLocationName(car.getLocation().getName());
				reservationOut.setModelId(car.getCarModel().getId());
				reservationOut.setModelName(car.getCarModel().getName());
				reservationOut.setManufacturerId(car.getCarModel().getCarManufacturer().getId());
				reservationOut.setManufacturerName(car.getCarModel().getCarManufacturer().getName());
				reservationOut.setFuelTypeName(car.getFuelType().getName());
				reservationOut.setFuelTypeId(car.getFuelType().getId());
				reservationOut.setTransmissionTypeName(car.getTransmissionType().getName());
				reservationOut.setTransmissionTypeId(car.getTransmissionType().getId());
				reservationOut.setMileage(car.getMileage());
				reservationOut.setChildSeats(car.getChildSeats());
				reservationOut.setPublisherId(car.getPublisherId());
				reservationOut.setPublisherTypeId(car.getPublisherType().getId());
				reservationOut.setPublisherTypeName(car.getPublisherType().getName());
				
				try {
					int sum = 0;
					int count = 0;
					List<ReservationDbModel> dbResponses = reservationRepo.findByCarId(reservationIn.getCarId());
					for (ReservationDbModel objectIn2 : dbResponses) {
						if (objectIn2.getRating() != null) {
							sum += objectIn2.getRating();
							count++;
						}
					}
					reservationOut.setRating(Math.round((float) sum / (float) count));
				} catch (Exception e) {
					System.out.println(e);
					continue;
				}
				
				//reservationOut.setRating(soapCarResponse.getCar().getRating());
				
				for (CarImageDbModel image : car.getImages()) {
					reservationOut.getImages().add(image.getId());
				}
				
				//reservationOut.getImage().addAll(soapCarResponse.getCar().getImage());
				//String publisherName = fetchPublisherName(soapCarResponse.getCar().getPublisherTypeName(), soapCarResponse.getCar().getPublisherId());
				//reservationOut.setPublisherName(publisherName);
				
				
				String publisherTypeName2 = car.getPublisherType().getName();
				if (publisherTypeName2.equals("USER")) {
					try {
						UserDbModel user2 = userRepo.findById(car.getPublisherId()).get();
						reservationOut.setPublisherName(user2.getFirstName() + " " + user2.getLastName());
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				// If agent
				if (publisherTypeName2.equals("AGENT")) {
					try {
						AgentDbModel agent = agentRepo.findById(car.getPublisherId()).get();
						reservationOut.setPublisherName(agent.getName());
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				//}

				//return reservationOut;
				
				internalResponse.add(reservationOut);
				
			}
		}
		return new ResponseEntity<>(internalResponse, HttpStatus.OK);
		
		/*
		 * if (!internalResponse.isSuccess()) { return new
		 * ResponseEntity<>(HttpStatus.NOT_FOUND); }
		 * 
		 * return new ResponseEntity<>(translator.translate(internalResponse),
		 * HttpStatus.OK);
		 */
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/reservations/report")
	public ResponseEntity<?> addRating(@RequestHeader("token") String token, @RequestBody AddReservationReportRequestModel request) {
		
//		SoapAddReservationReportRequest internalRequest = new SoapAddReservationReportRequest();
//		internalRequest.setComment(request.getComment());
//		internalRequest.setReservationId(request.getReservationId());
//		internalRequest.setToken(token);
//		
//		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		//SoapResponse response = new SoapResponse();

		Optional<ReservationDbModel> reservation = reservationRepo.findById(request.getReservationId());

		// Does the reservation exist?
		if (!reservation.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);
		
		Permission permission = tokenObj.getPermissions().stream()
				.filter(p -> p.getPermissionId() == 7 ).findFirst().orElse(null);

		if (!authanticated(tokenObj, reservation.get(), permission)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		response.setAuthorized(true);
		
		//Is the reservation complete?
		if (reservation.get().getBundle().getState().getId() != getCompletedState().getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//Is there already a report?
		if (reservation.get().getReportedMileage() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		reservation.get().setReportedMileage(request.getMileage());

		if(request.getComment() == null || request.getComment().trim().length() == 0) {
			reservation.get().setReportedComment(request.getComment());
		}

		reservationRepo.save(reservation.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
//		if (!internalResponse.isSuccess()) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
	
	public ReservationStateDbModel getCompletedState() {
		return reservationStateRepo.findById(6).get();
	}
}
