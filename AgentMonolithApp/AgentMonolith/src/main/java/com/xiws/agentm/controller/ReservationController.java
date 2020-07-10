package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;
import com.xiws.agentm.scheduleservice.data.dal.CarPriceListDbModel;
import com.xiws.agentm.scheduleservice.data.repo.BundleRepo;
import com.xiws.agentm.scheduleservice.data.repo.CarPriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationStateRepo;
import com.xiws.agentm.userservice.data.dal.UserDbModel;
import com.xiws.agentm.userservice.data.repo.UserRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
//import com.student.soap.contract.scheduleservice.SoapResponse;
import com.xiws.agentm.carservice.data.dal.CarDbModel;
import com.xiws.agentm.carservice.data.dal.CarImageDbModel;
import com.xiws.agentm.carservice.data.repo.CarRepo;
//import com.student.scheduleservice.data.dal.CarPriceListDbModel;
//import com.student.scheduleservice.data.dal.ReservationDbModel;
//import com.student.soap.contract.carservice.SoapCarRequest;
//import com.student.soap.contract.carservice.SoapCarResponse;
//import com.student.soap.contract.scheduleservice.Bundle;
//import com.student.soap.contract.scheduleservice.Reservation;
//import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.xiws.agentm.scheduleservice.data.dal.ReservationStateDbModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;
import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;
//import com.xiws.agentm.soap.contract.scheduleservice.SoapBundlesResponse;
import com.xiws.agentm.agentservice.data.dal.AgentDbModel;
import com.xiws.agentm.agentservice.data.repo.AgentRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import com.xiws.agentm.response.BundleResponseModel;
import com.xiws.agentm.response.ReservationResponseModel;

//import com.student.http.contract.HttpBundleResponse;
//import com.student.internal.translator.Translator;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
//import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
//import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
//import com.student.soap.contract.scheduleservice.SoapPublisherReservationsRequest;
//import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
//import com.student.soap.contract.scheduleservice.SoapReserveRequest;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.soap.contract.scheduleservice.SoapUserReservationsRequest;

@Controller
public class ReservationController {
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	BundleRepo bundleRepo;
	
	@Autowired
	ReservationStateRepo reservationStateRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AgentRepo agentRepo;
	
	@Autowired
	CarRepo carRepo;
	
	@Autowired
	CarPriceListRepo carPriceListRepo;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	/*
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public ReservationController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	*/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/user")
	public ResponseEntity<List<BundleResponseModel>> getUserReservations(@RequestHeader("token") String token) {
		/*
		SoapUserReservationsRequest internalRequest = new SoapUserReservationsRequest();
		internalRequest.setToken(token);
		SoapBundlesResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		List<BundleResponseModel> internalResponse = new ArrayList<BundleResponseModel>();
		
		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		if (!tokenObj.isValid() || tokenObj.getUserId() == null /* || !token.getRoleName().equals("BASIC") */) {
			//SoapBundlesResponse response = new SoapBundlesResponse();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// find all bundles not in cart
		List<BundleDbModel> bundles = bundleRepo.findByUserId(tokenObj.getUserId()).stream()
				.filter(bundle -> bundle.getState().getId() != getCartState().getId())
				.collect(Collectors.toList());
		
		//SoapBundlesResponse response = providerUtil.getSoapBundles(bundles);

		//SoapBundlesResponse output = new SoapBundlesResponse();

		for (BundleDbModel bundleIn : bundles) {
			BundleResponseModel bundleOut = new BundleResponseModel();
			bundleOut.setBundleId(bundleIn.getId());
			bundleOut.setPublisherId(bundleIn.getPublisherId());
			bundleOut.setPublisherTypeId(bundleIn.getPublisherType().getId());
			bundleOut.setPublisherTypeName(bundleIn.getPublisherType().getName());
			bundleOut.setUserId(bundleIn.getUserId());
			UserDbModel user = userRepo.findById(bundleIn.getUserId()).get();
			//bundleOut.setUserName(fetchPublisherName("USER", bundleIn.getUserId()));
			bundleOut.setUserName(user.getFirstName() + " " + user.getLastName());
			bundleOut.setStateId(bundleIn.getState().getId());
			bundleOut.setStateName(bundleIn.getState().getName());
			
			// Fetch publisher name
			/*
			bundleOut.setPublisherName(
					fetchPublisherName(bundleIn.getPublisherType().getName(), bundleIn.getPublisherId()));
			*/
			
			String publisherTypeName = bundleIn.getPublisherType().getName();
			if (publisherTypeName.equals("USER")) {
				try {
					UserDbModel user2 = userRepo.findById(bundleIn.getPublisherId()).get();
					bundleOut.setPublisherName(user2.getFirstName() + " " + user2.getLastName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}
			// If agent
			if (publisherTypeName.equals("AGENT")) {
				try {
					AgentDbModel agent = agentRepo.findById(bundleIn.getPublisherId()).get();
					bundleOut.setPublisherName(agent.getName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}

			for (ReservationDbModel reservationIn : bundleIn.getReservations()) {
				ReservationResponseModel reservationOut = new ReservationResponseModel();

				// fetch car
				/*
				SoapCarRequest soapCarRequest = new SoapCarRequest();
				soapCarRequest.setId(reservationIn.getCarId());

				SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);
				*/
				Optional<CarDbModel> carOptional = carRepo.findById(reservationIn.getCarId());
				if (!carOptional.isPresent() || !carOptional.get().isActive()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				CarDbModel car = carOptional.get();
				/*
				if (!soapCarResponse.isSuccess()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				*/

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut.setExtraCharges(reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = carPriceListRepo.findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
						.orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}
/*				
				reservationOut.setCarClassId(soapCarResponse.getCar().getCarClassId());
				reservationOut.setCarClassName(soapCarResponse.getCar().getCarClassName());
				reservationOut.setLocationId(soapCarResponse.getCar().getLocationId());
				reservationOut.setLocationName(soapCarResponse.getCar().getLocationName());
				reservationOut.setModelId(soapCarResponse.getCar().getModelId());
				reservationOut.setModelName(soapCarResponse.getCar().getModelName());
				reservationOut.setManufacturerId(soapCarResponse.getCar().getManufacturerId());
				reservationOut.setManufacturerName(soapCarResponse.getCar().getManufacturerName());
				reservationOut.setFuelTypeName(soapCarResponse.getCar().getFuelTypeName());
				reservationOut.setFuelTypeId(soapCarResponse.getCar().getFuelTypeId());
				reservationOut.setTransmissionTypeName(soapCarResponse.getCar().getTransmissionTypeName());
				reservationOut.setTransmissionTypeId(soapCarResponse.getCar().getTransmissionTypeId());
				reservationOut.setMileage(soapCarResponse.getCar().getMileage());
				reservationOut.setChildSeats(soapCarResponse.getCar().getChildSeats());
				reservationOut.setPublisherId(soapCarResponse.getCar().getPublisherId());
				reservationOut.setPublisherTypeId(soapCarResponse.getCar().getPublisherTypeId());
				reservationOut.setPublisherTypeName(soapCarResponse.getCar().getPublisherTypeName());
				reservationOut.setRating(soapCarResponse.getCar().getRating());
				reservationOut.getImages().addAll(soapCarResponse.getCar().getImage());
*/				
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
				
				bundleOut.getCars().add(reservationOut);
			}

			internalResponse.add(bundleOut);
		}

		return new ResponseEntity<List<BundleResponseModel>>(internalResponse, HttpStatus.OK);
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpBundleResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/publisher")
	public ResponseEntity<List<BundleResponseModel>> getPublisherReservations(@RequestHeader("token") String token) {
		/*
		SoapPublisherReservationsRequest internalRequest = new SoapPublisherReservationsRequest();
		internalRequest.setToken(token);
		SoapBundlesResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		List<BundleResponseModel> internalResponse = new ArrayList<BundleResponseModel>();
		
		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission requiredPermission = tokenObj.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == 5 ).findFirst().orElse(null);
		
		if(!authanticated(tokenObj, requiredPermission)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		// find all bundles that are not in cart
		List<BundleDbModel> bundles = bundleRepo
				.findByPublisherIdAndPublisherTypeId(requiredPermission.getResourceId(),  tokenObj.getRoleId())
				.stream()
				.filter(bundle -> bundle.getState().getId() != getCartState().getId())
				.collect(Collectors.toList());

		//SoapBundlesResponse response = providerUtil.getSoapBundles(bundles);
		
		
		
		for (BundleDbModel bundleIn : bundles) {
			BundleResponseModel bundleOut = new BundleResponseModel();
			bundleOut.setBundleId(bundleIn.getId());
			bundleOut.setPublisherId(bundleIn.getPublisherId());
			bundleOut.setPublisherTypeId(bundleIn.getPublisherType().getId());
			bundleOut.setPublisherTypeName(bundleIn.getPublisherType().getName());
			bundleOut.setUserId(bundleIn.getUserId());
			UserDbModel user = userRepo.findById(bundleIn.getUserId()).get();
			//bundleOut.setUserName(fetchPublisherName("USER", bundleIn.getUserId()));
			bundleOut.setUserName(user.getFirstName() + " " + user.getLastName());
			bundleOut.setStateId(bundleIn.getState().getId());
			bundleOut.setStateName(bundleIn.getState().getName());
			
			// Fetch publisher name
			/*
			bundleOut.setPublisherName(
					fetchPublisherName(bundleIn.getPublisherType().getName(), bundleIn.getPublisherId()));
			*/
			
			String publisherTypeName = bundleIn.getPublisherType().getName();
			if (publisherTypeName.equals("USER")) {
				try {
					UserDbModel user2 = userRepo.findById(bundleIn.getPublisherId()).get();
					bundleOut.setPublisherName(user2.getFirstName() + " " + user2.getLastName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}
			// If agent
			if (publisherTypeName.equals("AGENT")) {
				try {
					AgentDbModel agent = agentRepo.findById(bundleIn.getPublisherId()).get();
					bundleOut.setPublisherName(agent.getName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}

			for (ReservationDbModel reservationIn : bundleIn.getReservations()) {
				ReservationResponseModel reservationOut = new ReservationResponseModel();

				// fetch car
				/*
				SoapCarRequest soapCarRequest = new SoapCarRequest();
				soapCarRequest.setId(reservationIn.getCarId());

				SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);
				*/
				Optional<CarDbModel> carOptional = carRepo.findById(reservationIn.getCarId());
				if (!carOptional.isPresent() || !carOptional.get().isActive()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				CarDbModel car = carOptional.get();
				/*
				if (!soapCarResponse.isSuccess()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				*/

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut.setExtraCharges(reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = carPriceListRepo.findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
						.orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}
/*				
				reservationOut.setCarClassId(soapCarResponse.getCar().getCarClassId());
				reservationOut.setCarClassName(soapCarResponse.getCar().getCarClassName());
				reservationOut.setLocationId(soapCarResponse.getCar().getLocationId());
				reservationOut.setLocationName(soapCarResponse.getCar().getLocationName());
				reservationOut.setModelId(soapCarResponse.getCar().getModelId());
				reservationOut.setModelName(soapCarResponse.getCar().getModelName());
				reservationOut.setManufacturerId(soapCarResponse.getCar().getManufacturerId());
				reservationOut.setManufacturerName(soapCarResponse.getCar().getManufacturerName());
				reservationOut.setFuelTypeName(soapCarResponse.getCar().getFuelTypeName());
				reservationOut.setFuelTypeId(soapCarResponse.getCar().getFuelTypeId());
				reservationOut.setTransmissionTypeName(soapCarResponse.getCar().getTransmissionTypeName());
				reservationOut.setTransmissionTypeId(soapCarResponse.getCar().getTransmissionTypeId());
				reservationOut.setMileage(soapCarResponse.getCar().getMileage());
				reservationOut.setChildSeats(soapCarResponse.getCar().getChildSeats());
				reservationOut.setPublisherId(soapCarResponse.getCar().getPublisherId());
				reservationOut.setPublisherTypeId(soapCarResponse.getCar().getPublisherTypeId());
				reservationOut.setPublisherTypeName(soapCarResponse.getCar().getPublisherTypeName());
				reservationOut.setRating(soapCarResponse.getCar().getRating());
				reservationOut.getImages().addAll(soapCarResponse.getCar().getImage());
*/				
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
				if (publisherTypeName.equals("AGENT")) {
					try {
						AgentDbModel agent = agentRepo.findById(car.getPublisherId()).get();
						reservationOut.setPublisherName(agent.getName());
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				
				bundleOut.getCars().add(reservationOut);
			}
			
			internalResponse.add(bundleOut);
		}
		
		
		return new ResponseEntity<List<BundleResponseModel>>(internalResponse, HttpStatus.OK);
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<BundleResponseModel>>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/reserve")
	public ResponseEntity<List<BundleResponseModel>> reserve(@RequestHeader("token") String token) {
		/*
		SoapReserveRequest internalRequest = new SoapReserveRequest();
		internalRequest.setToken(token);
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		if (!tokenObj.isValid() || tokenObj.getUserId() == null /*|| !token.getRoleName().equals("BASIC")*/) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		//response.setAuthorized(true);

		List<BundleDbModel> bundles = bundleRepo.findByUserIdAndStateId(tokenObj.getUserId(),
				getCartState().getId());

		for (BundleDbModel bundle : bundles) {
			bundle.setState(getPendingState());
			bundleRepo.save(bundle);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/approve")
	public ResponseEntity<?> approve(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		/*
		SoapApproveReservationRequest internalRequest = new SoapApproveReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = bundleRepo.findById(bundleId);

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != getPendingState().getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		if (!isAuthorized(tokenObj, 5, bundle.get().getPublisherId(), bundle.get().getPublisherType().getId())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		bundle.get().setState(getPaidState());
		bundleRepo.save(bundle.get());

		List<BundleDbModel> rejectedBundles = bundleRepo
				.findByStateIdAndPublisherIdAndPublisherTypeId(getPendingState().getId(), bundle.get().getPublisherId(), bundle.get().getPublisherType().getId());
		
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
					if(datesOverlap(start1, end1, start2, end2)) {
						rejectedBundle.setState(getRejectedState());
						bundleRepo.save(rejectedBundle);
					}
				}
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/reject")
	public ResponseEntity<?> reject(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		/*
		SoapRejectReservationRequest internalRequest = new SoapRejectReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = bundleRepo.findById(bundleId);

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != getPendingState().getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		if (!isAuthorized(tokenObj, 5, bundle.get().getPublisherId(), bundle.get().getPublisherType().getId())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		bundle.get().setState(getRejectedState());
		bundleRepo.save(bundle.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/cancel")
	public ResponseEntity<?> cancel(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		/*
		SoapCancelReservationRequest internalRequest = new SoapCancelReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = bundleRepo.findById(bundleId);

		//Does the bundle exist? Is it in pending state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != getPendingState().getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		//Da li bundle pripada ovom korisniku?
		if (!tokenObj.isValid() || tokenObj.getUserId() != bundle.get().getUserId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		bundle.get().setState(getCanceledState());
		bundleRepo.save(bundle.get());
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
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
	
	public ReservationStateDbModel getCartState() {
		return reservationStateRepo.findById(1).get();
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
	
	public ReservationStateDbModel getPendingState() {
		return reservationStateRepo.findById(2).get();
	}
	
	public boolean isAuthorized(AuthenticationTokenParseResult token, int permissionId, Integer resourseId, int resourseTypeId) {
		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == permissionId).findFirst().orElse(null);
		
		if (!token.isValid() || requiredPermission == null)
		{
			return false;
		}
		
		boolean isAdmin = isAdmin(token);
		boolean isAgent = isResourseAgent(token.getRoleName(), requiredPermission, resourseId, resourseTypeId);
		boolean isUser =  isResourseUser(token.getRoleName(), requiredPermission, resourseId, resourseTypeId);
		
		if ( !isAdmin && !isAgent && !isUser ) 
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isAdmin(AuthenticationTokenParseResult tokenParseResult) {
		return tokenParseResult.getRoleName().equals("ADMIN");
	}
	
	public boolean isResourseAgent(String roleName, Permission permission, Integer resourseId, int resourseTypeId) {
		
		boolean isAgent = roleName.equals("AGENT");
		boolean isAgentPermission = permission.getResourceTypeName()!=null && permission.getResourceTypeName().equals("AGENT");
		boolean isResoursePermission = true;
		if(resourseId != null) {
			isResoursePermission = permission.getResourceId() == resourseId;			
		}
		boolean isAgentResourse = resourseTypeId == 2;
		
		return isAgent && isAgentPermission && isResoursePermission && isAgentResourse;
	}
	
	public boolean isResourseUser(String roleName, Permission permission, Integer resourseId, int resourseTypeId) {
		
		boolean isUser = roleName.equals("BASIC");
		boolean isUserPermission = permission.getResourceTypeName()!=null && permission.getResourceTypeName().equals("USER");
		boolean isResoursePermission = true;
		if(resourseId != null) {
			isResoursePermission = permission.getResourceId() == resourseId;			
		}
		boolean isUserResourse = resourseTypeId == 1;
		
		return isUser && isUserPermission && isResoursePermission && isUserResourse;
	}
	
	public ReservationStateDbModel getPaidState() {
		return reservationStateRepo.findById(4).get();
	}
	
	public boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
		return !start1.after(end2) && !start2.after(end1);
	}
	
	public ReservationStateDbModel getRejectedState() {
		return reservationStateRepo.findById(5).get();
	}
	
	public ReservationStateDbModel getCanceledState() {
		return reservationStateRepo.findById(3).get();
	}
	
}
