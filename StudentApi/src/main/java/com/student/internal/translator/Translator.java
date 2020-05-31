package com.student.internal.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpEditRequest;
import com.student.http.contract.HttpGetResponse;
import com.student.http.contract.HttpLoginRequest;
import com.student.http.contract.HttpLoginResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.http.contract.HttpRegisterRequest;
import com.student.http.contract.HttpSearchCarsRequest;
import com.student.soap.carservice.contract.Car;
import com.student.soap.carservice.contract.NamedObject;
import com.student.soap.carservice.contract.SoapAddCarRequest;
import com.student.soap.carservice.contract.SoapCarModelsResponse;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapSearchCarsRequest;
import com.student.soap.carservice.contract.SoapSearchCarsResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingRequest;
import com.student.soap.userservice.contract.SoapEditRequest;
import com.student.soap.userservice.contract.SoapGetRequest;
import com.student.soap.userservice.contract.SoapGetResponse;
import com.student.soap.userservice.contract.SoapLoginRequest;
import com.student.soap.userservice.contract.SoapLoginResponse;
import com.student.soap.userservice.contract.SoapRegisterRequest;
import com.student.soap.userservice.contract.SoapVerifyRequest;

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
		output.setPublisherTypeName(input.getCar().getPublisherName());
		output.setImages(input.getCar().getImage());
		output.setEstimatedPenalty(input.getCar().getEstimatedPenaltyPrice());

		return output;
	}

	public SoapAddCarRequest translate(HttpAddCarRequest input, String token) {
		SoapAddCarRequest output = new SoapAddCarRequest();
		output.setCarClassId(input.getCarClassId());
		output.setChildSeats(input.getChildSeats());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setMileage(input.getMileage());
		output.setModelId(input.getModelId());
		output.setPublisherId(input.getPublisherId());
		output.setPublisherTypeId(input.getPublisherTypeId());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		output.setToken(token);
		return output;
	}

	public SoapSearchCarsRequest translate(HttpSearchCarsRequest input) {
		SoapSearchCarsRequest output = new SoapSearchCarsRequest();
		
		output.setCarClassId(input.getCarClassId());
		output.setChildSeats(input.getChildSeats());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setLocationId(input.getLocationId());
		output.setManufacturerId(input.getManufacturerId());
		output.setMileage(input.getMileage());
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
	
	public HttpCarResponse translate(Car input) {
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
		output.setPublisherTypeName(input.getPublisherName());
		output.setImages(input.getImage());
		output.setEstimatedPenalty(input.getEstimatedPenaltyPrice());

		return output;
	}

	public List<HttpCarResponse> translate(SoapSearchCarsResponse input) {
		List<HttpCarResponse> output = new ArrayList<>();
		
		for(Car car: input.getCar()) {
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
}
