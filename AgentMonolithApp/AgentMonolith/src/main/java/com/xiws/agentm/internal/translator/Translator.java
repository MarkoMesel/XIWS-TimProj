package com.xiws.agentm.internal.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xiws.agentm.http.contract.HttpAddCarRequest;
import com.xiws.agentm.http.contract.HttpBundleResponse;
import com.xiws.agentm.http.contract.HttpCarModelResponse;
import com.xiws.agentm.http.contract.HttpCarRequest;
import com.xiws.agentm.http.contract.HttpCarResponse;
import com.xiws.agentm.http.contract.HttpCartAddCarRequest;
import com.xiws.agentm.http.contract.HttpCartBundleRequest;
import com.xiws.agentm.http.contract.HttpCorrespondenceResponse;
import com.xiws.agentm.http.contract.HttpEditRequest;
import com.xiws.agentm.http.contract.HttpGetResponse;
import com.xiws.agentm.http.contract.HttpLoginRequest;
import com.xiws.agentm.http.contract.HttpLoginResponse;
import com.xiws.agentm.http.contract.HttpNamedObjectResponse;
import com.xiws.agentm.http.contract.HttpPriceListResponse;
import com.xiws.agentm.http.contract.HttpPriceResponse;
import com.xiws.agentm.http.contract.HttpRegisterRequest;
import com.xiws.agentm.http.contract.HttpRepliesAndCommentsResponse;
import com.xiws.agentm.http.contract.HttpReservationResponse;
import com.xiws.agentm.http.contract.HttpSearchCarsRequest;
import com.xiws.agentm.http.contract.HttpUnavailabilityResponse;
import com.xiws.agentm.http.contract.HttpUserResponse;
import com.xiws.agentm.soap.contract.carservice.NamedObject;
import com.xiws.agentm.soap.contract.carservice.SoapAddCarRequest;
import com.xiws.agentm.soap.contract.carservice.SoapCarModelsResponse;
import com.xiws.agentm.soap.contract.carservice.SoapCarRequest;
import com.xiws.agentm.soap.contract.carservice.SoapCarResponse;
import com.xiws.agentm.soap.contract.carservice.SoapNamedObjectsResponse;
import com.xiws.agentm.soap.contract.carservice.SoapSearchCarsRequest;
import com.xiws.agentm.soap.contract.carservice.SoapSearchCarsResponse;
import com.xiws.agentm.soap.contract.scheduleservice.Bundle;
import com.xiws.agentm.soap.contract.scheduleservice.Correspondence;
import com.xiws.agentm.soap.contract.scheduleservice.Rating;
import com.xiws.agentm.soap.contract.scheduleservice.SoapBundlesResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapCarRatingRequest;
import com.xiws.agentm.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapCartAddCarRequest;
import com.xiws.agentm.soap.contract.scheduleservice.SoapCartBundleRequest;
import com.xiws.agentm.soap.contract.scheduleservice.SoapCartUnbundleRequest;
import com.xiws.agentm.soap.contract.scheduleservice.SoapMessagesResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapPrice;
import com.xiws.agentm.soap.contract.scheduleservice.SoapPriceList;
import com.xiws.agentm.soap.contract.scheduleservice.SoapPriceListResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapPriceListsResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapReservationsResponse;
import com.xiws.agentm.soap.contract.scheduleservice.SoapUnavailability;
import com.xiws.agentm.soap.contract.scheduleservice.SoapUnavailabilityResponse;
import com.xiws.agentm.soap.contract.userservice.SoapEditRequest;
import com.xiws.agentm.soap.contract.userservice.SoapGetRequest;
import com.xiws.agentm.soap.contract.userservice.SoapGetResponse;
import com.xiws.agentm.soap.contract.userservice.SoapLoginRequest;
import com.xiws.agentm.soap.contract.userservice.SoapLoginResponse;
import com.xiws.agentm.soap.contract.userservice.SoapRegisterRequest;
import com.xiws.agentm.soap.contract.userservice.SoapUser;
import com.xiws.agentm.soap.contract.userservice.SoapUsersResponse;
import com.xiws.agentm.soap.contract.userservice.SoapVerifyRequest;

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

		if (input.getObject() == null) {
			return output;
		}
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

	public List<HttpCarModelResponse> translate(SoapCarModelsResponse input) {
		List<HttpCarModelResponse> output = new ArrayList<>();

		if (input.getCarModels() == null || input.getCarModels().getCarModel() == null) {
			return output;
		}

		for (SoapCarModelsResponse.CarModels.CarModel carIn : input.getCarModels().getCarModel()) {
			HttpCarModelResponse carOut = new HttpCarModelResponse();
			carOut.setManufacturerId(carIn.getManufacturerId());
			carOut.setManufacturerName(carIn.getManufacturerName());
			carOut.setModelId(carIn.getModelId());
			carOut.setModelName(carIn.getModelName());
			output.add(carOut);
		}

		return output;
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

	public HttpCarResponse translate(com.xiws.agentm.soap.contract.carservice.Car input) {
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

	public List<HttpCarResponse> translate(SoapSearchCarsResponse input) {
		List<HttpCarResponse> output = new ArrayList<>();

		for (com.xiws.agentm.soap.contract.carservice.Car car : input.getCar()) {
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
				HttpCorrespondenceResponse replyOut = new HttpCorrespondenceResponse();
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

		if (input.getBundle() == null) {
			return output;
		}

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

			if (bundleIn.getReservation() == null) {
				continue;
			}

			for (com.xiws.agentm.soap.contract.scheduleservice.Reservation carIn : bundleIn.getReservation()) {
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

	public List<HttpCorrespondenceResponse> translate(List<Correspondence> input) {
		List<HttpCorrespondenceResponse> output = new ArrayList<>();

		if (input == null) {
			return output;
		}

		for (Correspondence replyIn : input) {
			HttpCorrespondenceResponse replyOut = new HttpCorrespondenceResponse();

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

	public List<HttpCorrespondenceResponse> translate(SoapPendingCommentsResponse input) {
		return translate(input.getPendingComment());
	}

	public List<HttpReservationResponse> translate(SoapReservationsResponse input) {
		List<HttpReservationResponse> output = new ArrayList<>();

		if (input.getReservation() == null) {
			return output;
		}

		for (com.xiws.agentm.soap.contract.scheduleservice.Reservation carIn : input.getReservation()) {
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

	public List<HttpCorrespondenceResponse> translate(SoapMessagesResponse input) {
		return translate(input.getMessage());
	}

	public List<HttpUnavailabilityResponse> translate(SoapUnavailabilityResponse input) {
		List<HttpUnavailabilityResponse> output = new ArrayList<>();

		if (input.getUnavailability() == null) {
			return output;
		}

		for (SoapUnavailability objIn : input.getUnavailability()) {
			HttpUnavailabilityResponse objOut = new HttpUnavailabilityResponse();
			objOut.setId(objIn.getId());
			objOut.setStartDate(objIn.getStartDate());
			objOut.setEndDate(objIn.getEndDate());

			output.add(objOut);
		}

		return output;
	}

	private HttpPriceResponse translate(SoapPrice input) {
		HttpPriceResponse output = new HttpPriceResponse();

		output.setEndDate(input.getEndDate());
		output.setStartDate(input.getStartDate());
		output.setId(input.getId());
		output.setPrice(input.getPrice());

		return output;
	}

	private HttpPriceListResponse translate(SoapPriceList input) {
		HttpPriceListResponse output = new HttpPriceListResponse();

		output.setDiscountPercentage(input.getDiscountPercentage());
		output.setId(input.getId());
		output.setMileagePenalty(input.getMileagePenalty());
		output.setMileageThreshold(input.getMileageThreshold());
		output.setName(input.getName());
		output.setWarrantyPrice(input.getWarrantyPrice());

		if (input.getPrice() == null) {
			return output;
		}

		for (SoapPrice priceIn : input.getPrice()) {
			output.getPrices().add(translate(priceIn));
		}

		return output;
	}

	public List<HttpPriceListResponse> translate(SoapPriceListsResponse input) {
		List<HttpPriceListResponse> output = new ArrayList<>();

		if (input.getPriceList() == null) {
			return output;
		}

		for (SoapPriceList priceListIn : input.getPriceList()) {
			output.add(translate(priceListIn));
		}

		return output;
	}

	public HttpPriceListResponse translate(SoapPriceListResponse input) {
		return translate(input.getPriceList());
	}

	public List<HttpUserResponse> translate(SoapUsersResponse input) {
		List<HttpUserResponse> output = new ArrayList<>();
		
		if(input.getUser() == null) {
			return output;
		}
		
		for(SoapUser in : input.getUser()) {
			HttpUserResponse out = new HttpUserResponse();
			out.setEmail(in.getEmail());
			out.setEmailVerified(in.isEmailVerified());
			out.setFirstName(in.getFirstName());
			out.setId(in.getId());
			out.setLastName(in.getLastName());
			out.setPhone(in.getPhone());
			out.setRoleId(in.getRoleId());
			out.setRoleName(in.getRoleName());
			out.setStatusId(in.getStatusId());
			out.setStatusName(in.getStatusName());
			
			output.add(out);
		}
		
		return output;
	}
}
