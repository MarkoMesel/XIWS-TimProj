package com.student.internal.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarAvailabilityRequest;
import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpCartAddCarRequest;
import com.student.http.contract.HttpCartBundleRequest;
import com.student.http.contract.HttpBundleResponse;
import com.student.http.contract.HttpCommentResponse;
import com.student.http.contract.HttpEditRequest;
import com.student.http.contract.HttpGetResponse;
import com.student.http.contract.HttpLoginRequest;
import com.student.http.contract.HttpLoginResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.http.contract.HttpRegisterRequest;
import com.student.http.contract.HttpRepliesAndCommentsResponse;
import com.student.http.contract.HttpReservationResponse;
import com.student.http.contract.HttpSearchCarsRequest;
import com.student.soap.contract.carservice.NamedObject;
import com.student.soap.contract.carservice.SoapAddCarRequest;
import com.student.soap.contract.carservice.SoapCarModelsResponse;
import com.student.soap.contract.carservice.SoapCarRequest;
import com.student.soap.contract.carservice.SoapCarResponse;
import com.student.soap.contract.carservice.SoapNamedObjectsResponse;
import com.student.soap.contract.carservice.SoapSearchCarsRequest;
import com.student.soap.contract.carservice.SoapSearchCarsResponse;
import com.student.soap.contract.scheduleservice.Bundle;
import com.student.soap.contract.scheduleservice.Correspondence;
import com.student.soap.contract.scheduleservice.Rating;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCarAvailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapCartAddCarRequest;
import com.student.soap.contract.scheduleservice.SoapCartBundleRequest;
import com.student.soap.contract.scheduleservice.SoapCartUnbundleRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingRatingResponse;
import com.student.soap.contract.userservice.SoapEditRequest;
import com.student.soap.contract.userservice.SoapGetRequest;
import com.student.soap.contract.userservice.SoapGetResponse;
import com.student.soap.contract.userservice.SoapLoginRequest;
import com.student.soap.contract.userservice.SoapLoginResponse;
import com.student.soap.contract.userservice.SoapRegisterRequest;
import com.student.soap.contract.userservice.SoapVerifyRequest;

@Component("Translator")
public class Translator {
	
	// Login
	public SoapLoginRequest translate(HttpLoginRequest input) {
		SoapLoginRequest output = new SoapLoginRequest();
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		return output;
	}

	public HttpLoginResponse httpTranslate(SoapLoginResponse input) {
		HttpLoginResponse output = new HttpLoginResponse();
		output.setToken(input.getToken());
		output.setId(input.getId());
		output.setRoleId(input.getRoleId());
		output.setRoleName(input.getRoleName());
		return output;
	}

	// Register
	public SoapRegisterRequest translate(HttpRegisterRequest input) {
		SoapRegisterRequest output = new SoapRegisterRequest();
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPhone(input.getPhone());
		return output;
	}

	// Edit
	public SoapEditRequest translate(HttpEditRequest input, String token) {
		SoapEditRequest output = new SoapEditRequest();
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPassword(input.getPassword());
		output.setPhone(input.getPhone());
		output.setToken(token);
		return output;
	}

	// Get
	public SoapGetRequest translateGet(String token) {
		SoapGetRequest output = new SoapGetRequest();
		output.setToken(token);
		return output;
	}

	public HttpGetResponse translate(SoapGetResponse input) {
		HttpGetResponse output = new HttpGetResponse();
		output.setEmail(input.getEmail());
		output.setPhone(input.getPhone());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		return output;
	}

	// Verify
	public SoapVerifyRequest translateVerify(String token) {
		SoapVerifyRequest output = new SoapVerifyRequest();
		output.setToken(token);
		return output;
	}

	// Generic

	public List<HttpNamedObjectResponse> translate(SoapNamedObjectsResponse input) {
		List<HttpNamedObjectResponse> output = new ArrayList<>();

		for (NamedObject objectIn : input.getObject()) {
			HttpNamedObjectResponse objectOut = new HttpNamedObjectResponse();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.add(objectOut);
		}
		return output;
	}

	// Car
	public SoapCarRatingRequest translateCarRating(int id) {
		SoapCarRatingRequest output = new SoapCarRatingRequest();
		output.setId(id);

		return output;
	}

	public List<HttpCarModelResponse> translate(SoapCarModelsResponse internalResponse) {
		List<HttpCarModelResponse> response = new ArrayList<>();

		for (SoapCarModelsResponse.CarModels.CarModel carIn : internalResponse.getCarModels().getCarModel()) {
			HttpCarModelResponse carOut = new HttpCarModelResponse();
			carOut.setManufacturerId(carIn.getManufacturerId());
			carOut.setManufacturerName(carIn.getManufacturerName());
			carOut.setModelId(carIn.getModelId());
			carOut.setModelName(carIn.getModelName());
			response.add(carOut);
		}

		return response;
	}

	public HttpCarResponse translate(SoapCarResponse input) {
		HttpCarResponse output = new HttpCarResponse();

		output.setId(input.getCar().getId());
		output.setModelId(input.getCar().getModelId());
		output.setModelName(input.getCar().getModelName());
		output.setManufacturerId(input.getCar().getManufacturerId());
		output.setManufacturerName(input.getCar().getManufacturerName());
		output.setFuelTypeId(input.getCar().getFuelTypeId());
		output.setFuelTypeName(input.getCar().getFuelTypeName());
		output.setTransmissionTypeId(input.getCar().getTransmissionTypeId());
		output.setTransmissionTypeName(input.getCar().getTransmissionTypeName());
		output.setCarClassId(input.getCar().getCarClassId());
		output.setCarClassName(input.getCar().getCarClassName());
		output.setMileage(input.getCar().getMileage());
		output.setMileageThreshold(input.getCar().getMileageThreshold());
		output.setMileagePenalty(input.getCar().getMileagePenalty());
		output.setCollisionWaranty(input.getCar().getCollisionWaranty());
		output.setPrice(input.getCar().getPrice());
		output.setDiscount(input.getCar().getDiscount());
		output.setTotalPrice(input.getCar().getTotalPrice());
		output.setChildSeats(input.getCar().getChildSeats());
		output.setRating(input.getCar().getRating());
		output.setPublisherId(input.getCar().getPublisherId());
		output.setPublisherName(input.getCar().getPublisherName());
		output.setPublisherTypeId(input.getCar().getPublisherTypeId());
		output.setPublisherTypeName(input.getCar().getPublisherTypeName());
		output.setImages(input.getCar().getImage());
		output.setEstimatedPenalty(input.getCar().getEstimatedPenaltyPrice());
		output.setLocationId(input.getCar().getLocationId());
		output.setLocationName(input.getCar().getLocationName());

		return output;
	}

	public SoapAddCarRequest translate(HttpAddCarRequest input, String token) {
		SoapAddCarRequest output = new SoapAddCarRequest();
		output.setCarClassId(input.getCarClassId());
		output.setChildSeats(input.getChildSeats());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setMileage(input.getMileage());
		output.setModelId(input.getModelId());
		output.setLocationId(input.getLocationId());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		output.setToken(token);
		return output;
	}

	public SoapSearchCarsRequest translate(HttpSearchCarsRequest input) {
		SoapSearchCarsRequest output = new SoapSearchCarsRequest();

		output.setCarClassId(input.getCarClassId());
		output.setMinChildSeats(input.getMinChildSeats());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setLocationId(input.getLocationId());
		output.setManufacturerId(input.getManufacturerId());
		output.setMinMileage(input.getMinMileage());
		output.setMaxMileage(input.getMaxMileage());
		output.setModelId(input.getModelId());
		output.setPublisherTypeId(input.getPublisherTypeId());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		output.setEndDate(input.getEndDate());
		output.setStartDate(input.getStartDate());
		output.setCollisionWarranty(input.getCollisionWarranty());
		output.setPlannedMileage(input.getPlannedMileage());
		output.setMaxPrice(input.getMaxPrice());
		output.setMinPrice(input.getMinPrice());

		return output;
	}

	public HttpCarResponse translate(com.student.soap.contract.carservice.Car input) {
		HttpCarResponse output = new HttpCarResponse();

		output.setId(input.getId());
		output.setModelId(input.getModelId());
		output.setModelName(input.getModelName());
		output.setManufacturerId(input.getManufacturerId());
		output.setManufacturerName(input.getManufacturerName());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setFuelTypeName(input.getFuelTypeName());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		output.setTransmissionTypeName(input.getTransmissionTypeName());
		output.setCarClassId(input.getCarClassId());
		output.setCarClassName(input.getCarClassName());
		output.setMileage(input.getMileage());
		output.setMileageThreshold(input.getMileageThreshold());
		output.setMileagePenalty(input.getMileagePenalty());
		output.setCollisionWaranty(input.getCollisionWaranty());
		output.setPrice(input.getPrice());
		output.setDiscount(input.getDiscount());
		output.setTotalPrice(input.getTotalPrice());
		output.setChildSeats(input.getChildSeats());
		output.setRating(input.getRating());
		output.setPublisherId(input.getPublisherId());
		output.setPublisherName(input.getPublisherName());
		output.setPublisherTypeId(input.getPublisherTypeId());
		output.setPublisherTypeName(input.getPublisherTypeName());
		output.setImages(input.getImage());
		output.setEstimatedPenalty(input.getEstimatedPenaltyPrice());
		output.setLocationId(input.getLocationId());
		output.setLocationName(input.getLocationName());

		return output;
	}

	public SoapCarRatingsAndCommentsRequest translateRatingsAndComments(int id) {
		SoapCarRatingsAndCommentsRequest output = new SoapCarRatingsAndCommentsRequest();
		output.setId(id);
		return output;
	}

	public List<HttpCarResponse> translate(SoapSearchCarsResponse input) {
		List<HttpCarResponse> output = new ArrayList<>();

		for (com.student.soap.contract.carservice.Car car : input.getCar()) {
			output.add(translate(car));
		}

		return output;
	}

	public SoapCarRequest translate(HttpCarRequest input) {
		SoapCarRequest output = new SoapCarRequest();

		output.setId(input.getId());
		output.setPlannedMileage(input.getPlannedMileage());
		output.setStartDate(input.getStartDate());
		output.setEndDate(input.getEndDate());

		return output;
	}

	public List<HttpRepliesAndCommentsResponse> translate(SoapCarRatingsAndCommentsResponse internalResponse) {
		List<HttpRepliesAndCommentsResponse> response = new ArrayList<>();

		for (Rating commIn : internalResponse.getComment()) {
			HttpRepliesAndCommentsResponse commOut = new HttpRepliesAndCommentsResponse();
			commOut.setComment(commIn.getComment());
			commOut.setRating(commIn.getRating());
			commOut.setDate(commIn.getDate());
			commOut.setUserId(commIn.getUserId());
			commOut.setUserName(commIn.getUserName());

			for (Correspondence replyIn : commIn.getReply()) {
				HttpCommentResponse replyOut = new HttpCommentResponse();
				replyOut.setComment(replyIn.getComment());
				replyOut.setDate(replyIn.getDate());
				replyOut.setPublisherId(replyIn.getPublisherId());
				replyOut.setPublisherName(replyIn.getPublisherName());
				replyOut.setPublisherTypeId(replyIn.getPublisherTypeId());
				replyOut.setPublisherTypeName(replyIn.getPublisherTypeName());
				replyOut.setId(replyIn.getId());

				commOut.getReplies().add(replyOut);
			}

			response.add(commOut);
		}

		return response;
	}

	public SoapCarAvailabilityRequest translate(HttpCarAvailabilityRequest input) {
		SoapCarAvailabilityRequest output = new SoapCarAvailabilityRequest();

		output.setId(input.getId());
		output.setStartDate(input.getStartDate());
		output.setEndDate(input.getEndDate());

		return output;
	}

	public SoapCartAddCarRequest translate(String token, HttpCartAddCarRequest input) {
		SoapCartAddCarRequest output = new SoapCartAddCarRequest();

		output.setToken(token);
		output.setCarId(input.getCarId());
		output.setStartDate(input.getStartDate());
		output.setEndDate(input.getEndDate());
		output.setCollisionWarranty(input.isCollisionWarranty());

		return output;
	}

	public SoapCartBundleRequest translateBundle(String token, HttpCartBundleRequest input) {
		SoapCartBundleRequest output = new SoapCartBundleRequest();

		output.setToken(token);
		output.setPublisherId(input.getPublisherId());
		output.setPublisherTypeId(input.getPublisherTypeId());
		return output;
	}

	public SoapCartUnbundleRequest translateUnbundle(String token, HttpCartBundleRequest input) {
		SoapCartUnbundleRequest output = new SoapCartUnbundleRequest();

		output.setToken(token);
		output.setPublisherId(input.getPublisherId());
		output.setPublisherTypeId(input.getPublisherTypeId());
		return output;
	}

	public List<HttpBundleResponse> translate(SoapBundlesResponse input) {
		List<HttpBundleResponse> output = new ArrayList<>();

		for (Bundle bundleIn : input.getBundle()) {
			HttpBundleResponse bundleOut = new HttpBundleResponse();
			bundleOut.setBundleId(bundleIn.getBundleId());
			bundleOut.setPublisherId(bundleIn.getPublisherId());
			bundleOut.setPublisherName(bundleIn.getPublisherName());
			bundleOut.setPublisherTypeId(bundleIn.getPublisherTypeId());
			bundleOut.setPublisherTypeName(bundleIn.getPublisherTypeName());
			bundleOut.setStateId(bundleIn.getStateId());
			bundleOut.setStateName(bundleIn.getStateName());
			bundleOut.setUserId(bundleIn.getUserId());
			bundleOut.setUserName(bundleIn.getUserName());
			
			for (com.student.soap.contract.scheduleservice.Car carIn : bundleIn.getCar()) {
				HttpReservationResponse carOut = new HttpReservationResponse();

				carOut.setReservationId(carIn.getReservationId());
				carOut.setCarId(carIn.getCarId());
				carOut.setWarrantyIncluded(carIn.isWarrantyIncluded());
				carOut.setTotalPrice(carIn.getTotalPrice());
				carOut.setMileagePenalty(carIn.getMileagePenalty());
				carOut.setMileageThreshold(carIn.getMileageThreshold());
				carOut.setCarClassId(carIn.getCarClassId());
				carOut.setCarClassName(carIn.getCarClassName());
				carOut.setLocationId(carIn.getLocationId());
				carOut.setLocationName(carIn.getLocationName());
				carOut.setModelId(carIn.getModelId());
				carOut.setModelName(carIn.getModelName());
				carOut.setManufacturerId(carIn.getManufacturerId());
				carOut.setManufacturerName(carIn.getManufacturerName());
				carOut.setFuelTypeName(carIn.getFuelTypeName());
				carOut.setFuelTypeId(carIn.getFuelTypeId());
				carOut.setTransmissionTypeName(carIn.getTransmissionTypeName());
				carOut.setTransmissionTypeId(carIn.getTransmissionTypeId());
				carOut.setMileage(carIn.getMileage());
				carOut.setChildSeats(carIn.getChildSeats());
				carOut.setPublisherId(carIn.getPublisherId());
				carOut.setPublisherTypeId(carIn.getPublisherTypeId());
				carOut.setPublisherTypeName(carIn.getPublisherTypeName());
				carOut.setPublisherName(carIn.getPublisherName());
				carOut.setRating(carIn.getRating());
				carOut.getImages().addAll(carIn.getImage());

				bundleOut.getCars().add(carOut);
			}

			output.add(bundleOut);
		}

		return output;
	}

	public List<HttpCommentResponse> translate(SoapPendingCommentsResponse input) {
		 List<HttpCommentResponse> output = new ArrayList<>();
		 
		for( Correspondence replyIn : input.getPendingComment()) {
			HttpCommentResponse replyOut = new HttpCommentResponse();
			
			replyOut.setComment(replyIn.getComment());
			replyOut.setDate(replyIn.getDate());
			replyOut.setPublisherId(replyIn.getPublisherId());
			replyOut.setPublisherName(replyIn.getPublisherName());
			replyOut.setPublisherTypeId(replyIn.getPublisherTypeId());
			replyOut.setPublisherTypeName(replyIn.getPublisherTypeName());
			replyOut.setId(replyIn.getId());
			
			output.add(replyOut);
		}
		
		return output;
	}

	public List<HttpReservationResponse> translate(SoapPendingRatingResponse input) {
		List<HttpReservationResponse> output = new ArrayList<>();
		
		for (com.student.soap.contract.scheduleservice.Car carIn : input.getCar()) {
			HttpReservationResponse carOut = new HttpReservationResponse();

			carOut.setReservationId(carIn.getReservationId());
			carOut.setCarId(carIn.getCarId());
			carOut.setWarrantyIncluded(carIn.isWarrantyIncluded());
			carOut.setTotalPrice(carIn.getTotalPrice());
			carOut.setMileagePenalty(carIn.getMileagePenalty());
			carOut.setMileageThreshold(carIn.getMileageThreshold());
			carOut.setCarClassId(carIn.getCarClassId());
			carOut.setCarClassName(carIn.getCarClassName());
			carOut.setLocationId(carIn.getLocationId());
			carOut.setLocationName(carIn.getLocationName());
			carOut.setModelId(carIn.getModelId());
			carOut.setModelName(carIn.getModelName());
			carOut.setManufacturerId(carIn.getManufacturerId());
			carOut.setManufacturerName(carIn.getManufacturerName());
			carOut.setFuelTypeName(carIn.getFuelTypeName());
			carOut.setFuelTypeId(carIn.getFuelTypeId());
			carOut.setTransmissionTypeName(carIn.getTransmissionTypeName());
			carOut.setTransmissionTypeId(carIn.getTransmissionTypeId());
			carOut.setMileage(carIn.getMileage());
			carOut.setChildSeats(carIn.getChildSeats());
			carOut.setPublisherId(carIn.getPublisherId());
			carOut.setPublisherName(carIn.getPublisherName());
			carOut.setPublisherTypeId(carIn.getPublisherTypeId());
			carOut.setPublisherTypeName(carIn.getPublisherTypeName());
			carOut.setRating(carIn.getRating());
			carOut.getImages().addAll(carIn.getImage());
			carOut.setExtraCharges(carIn.getExtraCharges());

			output.add(carOut);
		}

		return output;
	}

}
