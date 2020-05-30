package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarClassDbModel;
import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.dal.CarManufacturerDbModel;
import com.student.data.dal.CarModelDbModel;
import com.student.data.dal.FuelTypeDbModel;
import com.student.data.dal.TransmissionTypeDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalAddCarRequest;
import com.student.internal.contract.InternalAutherisedResponse;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalCarsResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.contract.SoapAddCarClassRequest;
import com.student.soap.contract.SoapAddCarModelRequest;
import com.student.soap.contract.SoapAddFuelTypeRequest;
import com.student.soap.contract.SoapAddManufacturerRequest;
import com.student.soap.contract.SoapAddTransmissionTypeRequest;
import com.student.soap.contract.SoapCarRequest;
import com.student.soap.contract.SoapCarResponse;
import com.student.soap.contract.SoapDeleteCarClassRequest;
import com.student.soap.contract.SoapDeleteCarModelRequest;
import com.student.soap.contract.SoapDeleteFuelTypeRequest;
import com.student.soap.contract.SoapDeleteImageRequest;
import com.student.soap.contract.SoapDeleteManufacturerRequest;
import com.student.soap.contract.SoapDeleteTransmissionTypeRequest;
import com.student.soap.contract.SoapGetImageRequest;
import com.student.soap.contract.SoapGetImageResponse;
import com.student.soap.contract.SoapPostImageRequest;
import com.student.soap.contract.SoapPostImageResponse;
import com.student.soap.contract.SoapResponse;
import com.student.soap.resource.client.ScheduleServiceClient;
import com.student.soap.resourse.contract.SoapCarPriceResponse;
import com.student.soap.resourse.contract.SoapCarRatingResponse;

@Component("CarProvider")
public class CarProvider {

	private UnitOfWork unitOfWork;
	private ScheduleServiceClient scheduleServiceClient;
	private JwtUtil jwtUtil;

	@Autowired
	public CarProvider(UnitOfWork unitOfWork, ScheduleServiceClient scheduleServiceClient, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.scheduleServiceClient = scheduleServiceClient;
		this.jwtUtil = jwtUtil;
	}

	public InternalAutherisedResponse addCar(InternalAddCarRequest request) {
		InternalAutherisedResponse response = new InternalAutherisedResponse();
		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		//TODO
		return response;

		/*
		response.setAutherised(true);

		CarDbModel car = new CarDbModel();
		car.setCarClass(unitOfWork.getCarClassRepo().findById(request.getCarClassId()).get());
		car.setCarModel(unitOfWork.getCarModelRepo().findById(request.getModelId()).get());
		car.setChildSeats(request.getChildSeats());
		car.setFuelType(unitOfWork.getFuelTypeRepo().findById(request.getFuelTypeId()).get());
		car.setMileage(request.getMileage());
		car.setTransmissionType(unitOfWork.getTransmissionTypeRepo().findById(request.getTransmissionTypeId()).get());
		//TODO: car.setPublisherType(unitOfWork.getPublisherTypeRepo().findById(addCarPermission.getResourceTypeId()).get());
		//TODO: car.setPublisherId(addCarPermission.getResourceId());

		unitOfWork.getCarRepo().save(car);

		response.setSuccess(true);
		return response;
		*/
	}

	public InternalCarModelsResponse getAllCarModels() {
		InternalCarModelsResponse response = new InternalCarModelsResponse();
		unitOfWork.getCarModelRepo().findAll().forEach(carModel -> {
			response.addCarModel(new InternalCarModelsResponse.CarModel(carModel.getId(), carModel.getName(),
					carModel.getCarManufacturer().getId(), carModel.getCarManufacturer().getName()));
		});

		response.setSuccess(true);
		return response;
	}

	public InternalCarModelsResponse getCarModelsByManufacturerId(int manufacturerId) {
		InternalCarModelsResponse response = new InternalCarModelsResponse();

		if (!unitOfWork.getCarManufacturerRepo().findById(manufacturerId).isPresent()) {
			response.setSuccess(false);
			return response;
		}

		unitOfWork.getCarModelRepo().findByManufacturerId(manufacturerId).forEach(carModel -> {
			response.addCarModel(new InternalCarModelsResponse.CarModel(carModel.getId(), carModel.getName(),
					carModel.getCarManufacturer().getId(), carModel.getCarManufacturer().getName()));
		});

		response.setSuccess(true);
		return response;
	}

	public InternalNamedObjectsResponse getCarManufacturers() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getCarManufacturerRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public InternalNamedObjectsResponse getFuelTypes() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getFuelTypeRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public InternalNamedObjectsResponse getTransmissionTypes() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getTransmissionTypeRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public InternalNamedObjectsResponse getCarClasses() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getCarClassRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public SoapGetImageResponse getCarImage(SoapGetImageRequest request) {
		SoapGetImageResponse response = new SoapGetImageResponse();

		Optional<CarImageDbModel> image = unitOfWork.getCarImageRepo().findById(request.getId());
		if (!image.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		response.setId(request.getId());
		response.setCarId(image.get().getCar().getId());
		response.setImage(image.get().getImage());
		
		response.setSuccess(true);
		return response;
	}

	public InternalCarsResponse getAllCars() {
		InternalCarsResponse response = new InternalCarsResponse();

		for (CarDbModel objectIn : unitOfWork.getCarRepo().findAll()) {
			InternalCarsResponse.Car objectOut = new InternalCarsResponse.Car();

			objectOut.setId(objectIn.getId());
			objectOut.setModelId(objectIn.getCarModel().getId());
			objectOut.setModelName(objectIn.getCarModel().getName());
			objectOut.setManufacturerId(objectIn.getCarModel().getCarManufacturer().getId());
			objectOut.setManufacturerName(objectIn.getCarModel().getCarManufacturer().getName());
			objectOut.setFuelType(objectIn.getFuelType().getName());
			objectOut.setFuelTypeId(objectIn.getFuelType().getId());
			objectOut.setTransmission(objectIn.getTransmissionType().getName());
			objectOut.setTransmissionTypeId(objectIn.getTransmissionType().getId());
			objectOut.setCarClass(objectIn.getCarClass().getName());
			objectOut.setCarClassId(objectIn.getCarClass().getId());
			objectOut.setMileage(objectIn.getMileage());
			objectOut.setChildSeats(objectIn.getChildSeats());
			objectOut.setAgentId(23);
			objectOut.setAgentName("TODO");
			objectIn.getImages().forEach(image -> {
				objectOut.getCarImages().add(image.getId());
			});

			SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(objectIn.getId());
			if (!carRatingResponse.isSuccess()) {
				response = new InternalCarsResponse();
				response.setSuccess(false);
				return response;
			}
			objectOut.setCarRating(carRatingResponse.getRating());

			// TODO: search - scheduleServiceClient.getCarPrice
			/*
			 * SoapCarPriceResponse carPriceResponse =
			 * scheduleServiceClient.getCarPrice(objectIn.getId(), );
			 * if(!carRatingResponse.isSuccess()) { response = new InternalCarsResponse();
			 * response.setSuccess(false); return response; }
			 * objectOut.setPricePerDay(carPriceResponse.getPricePerDay());
			 * objectOut.setCollisionWaranty(carPriceResponse.isCollisionWarranty());
			 * objectOut.setMileageThreshold(carPriceResponse.getMileageThreshold());
			 * objectOut.setMileagePenalty(carPriceResponse.getMileageThreshold());
			 */

			response.addCar(objectOut);
		}

		response.setSuccess(true);
		return response;
	}

	/*
	 * public InternalCarResponse getCar(int id) { InternalCarResponse response =
	 * new InternalCarResponse();
	 * 
	 * Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(id); if
	 * (!car.isPresent()) { response.setSuccess(false); return response; }
	 * response.setId(car.get().getId());
	 * response.setModelId(car.get().getCarModel().getId());
	 * response.setModelName(car.get().getCarModel().getName());
	 * response.setManufacturerId(car.get().getCarModel().getCarManufacturer().getId
	 * ());
	 * response.setManufacturerName(car.get().getCarModel().getCarManufacturer().
	 * getName()); response.setFuelType(car.get().getFuelType().getName());
	 * response.setFuelTypeId(car.get().getFuelType().getId());
	 * response.setTransmission(car.get().getTransmissionType().getName());
	 * response.setTransmissionTypeId(car.get().getTransmissionType().getId());
	 * response.setCarClass(car.get().getCarClass().getName());
	 * response.setCarClassId(car.get().getCarClass().getId());
	 * response.setMileage(car.get().getMileage());
	 * response.setChildSeats(car.get().getChildSeats()); response.setAgentId(23);
	 * response.setAgentName("TODO");
	 * 
	 * SoapCarRatingResponse carRatingResponse =
	 * scheduleServiceClient.getCarRating(id); if (!carRatingResponse.isSuccess()) {
	 * response = new InternalCarResponse(); response.setSuccess(false); return
	 * response; } response.setCarRating(carRatingResponse.getRating());
	 * 
	 * //TODO: getCar - scheduleServiceClient.getCarPrice /* SoapCarPriceResponse
	 * carPriceResponse = scheduleServiceClient.getCarPrice(objectIn.getId(), );
	 * if(!carRatingResponse.isSuccess()) { response = new InternalCarsResponse();
	 * response.setSuccess(false); return response; }
	 * objectOut.setPricePerDay(carPriceResponse.getPricePerDay());
	 * objectOut.setCollisionWaranty(carPriceResponse.isCollisionWarranty());
	 * objectOut.setMileageThreshold(carPriceResponse.getMileageThreshold());
	 * objectOut.setMileagePenalty(carPriceResponse.getMileageThreshold());
	 * 
	 * 
	 * for (CarImageDbModel image : car.get().getImages()) {
	 * response.getCarImages().add(image.getId()); }
	 * 
	 * response.setSuccess(true); return response; }
	 */

	public SoapCarResponse getCar(SoapCarRequest request) {
		SoapCarResponse response = new SoapCarResponse();

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getId());
		if (!car.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		response.setId(car.get().getId());
		response.setModelId(car.get().getCarModel().getId());
		response.setModelName(car.get().getCarModel().getName());
		response.setManufacturerId(car.get().getCarModel().getCarManufacturer().getId());
		response.setManufacturerName(car.get().getCarModel().getCarManufacturer().getName());
		response.setFuelTypeName(car.get().getFuelType().getName());
		response.setFuelTypeId(car.get().getFuelType().getId());
		response.setTransmissionTypeName(car.get().getTransmissionType().getName());
		response.setTransmissionTypeId(car.get().getTransmissionType().getId());
		response.setCarClassName(car.get().getCarClass().getName());
		response.setCarClassId(car.get().getCarClass().getId());
		response.setMileage(car.get().getMileage());
		response.setChildSeats(car.get().getChildSeats());
		response.setPublisherId(car.get().getPublisherId());
		response.setPublisherName("TODO");
		response.setPublisherTypeId(car.get().getPublisherType().getId());
		response.setPublisherTypeName(car.get().getPublisherType().getName());

		SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(request.getId());
		if (!carRatingResponse.isSuccess()) {
			response = new SoapCarResponse();
			response.setSuccess(false);
			return response;
		}
		response.setRating(carRatingResponse.getRating());

		SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(request.getId(), request.getStartDate(), request.getEndDate());
		if (!carRatingResponse.isSuccess()) {
			response = new SoapCarResponse();
			response.setSuccess(false);
			return response;
		}

		response.setCollisionWaranty(carPriceResponse.getCollisionWarranty());
		response.setMileagePenalty(carPriceResponse.getMileagePenalty());
		response.setMileageThreshold(carPriceResponse.getMileageThreshold());
		response.setPrice(carPriceResponse.getPrice());
		response.setDiscount(carPriceResponse.getDiscount());
		response.setTotalPrice(carPriceResponse.getTotalPrice());

		for (CarImageDbModel image : car.get().getImages()) {
			response.getImage().add(image.getId());
		}

		response.setSuccess(true);
		return response;
	}

	public SoapPostImageResponse postCarImage(SoapPostImageRequest request) {
		SoapPostImageResponse response = new SoapPostImageResponse();
		
		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getCarId());
		if(!car.isPresent()) {
			return new SoapPostImageResponse();
		}
		
		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());
		
		if(!jwtUtil.isAutharized(tokenParseResult, 2, car.get().getId(), car.get().getPublisherType().getName())) {
			response.setAuthorized(true);
			return response;
		}
		
		response.setAuthorized(true);
		
		CarImageDbModel image = new CarImageDbModel();
		
		image.setCar(car.get());
		image.setImage(request.getImage());
		unitOfWork.getCarImageRepo().save(image);
		
		response.setImageId(image.getId());
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse deleteCarImage(SoapDeleteImageRequest request) {
		SoapResponse response = new SoapResponse();
		
		Optional<CarImageDbModel> image = unitOfWork.getCarImageRepo().findById(request.getId());
		if(!image.isPresent()) {
			return new SoapResponse();
		}
		
		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());
		
		if(!jwtUtil.isAutharized(tokenParseResult, 2, image.get().getCar().getId(), image.get().getCar().getPublisherType().getName())) {
			response.setAuthorized(true);
			return response;
		}
		
		response.setAuthorized(true);

		unitOfWork.getCarImageRepo().delete(image.get());
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse addCarClass(SoapAddCarClassRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		CarClassDbModel carClass = new CarClassDbModel();
		
		carClass.setName(request.getName());
		unitOfWork.getCarClassRepo().save(carClass);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarClass(SoapDeleteCarClassRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<CarClassDbModel> carClass = unitOfWork.getCarClassRepo().findById(request.getId());
		if(!carClass.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getCarClassRepo().delete(carClass.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addManufacturer(SoapAddManufacturerRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		CarManufacturerDbModel manufacturer = new CarManufacturerDbModel();
		
		manufacturer.setName(request.getName());
		unitOfWork.getCarManufacturerRepo().save(manufacturer);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteManufacturer(SoapDeleteManufacturerRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo().findById(request.getId());
		if(!manufacturer.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getCarManufacturerRepo().delete(manufacturer.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addCarModel(SoapAddCarModelRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo().findById(request.getManufacturerId());
		if(!manufacturer.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		CarModelDbModel carModel = new CarModelDbModel();
		
		carModel.setName(request.getName());
		carModel.setCarManufacturer(manufacturer.get());
		unitOfWork.getCarModelRepo().save(carModel);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarModel(SoapDeleteCarModelRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<CarModelDbModel> carModel = unitOfWork.getCarModelRepo().findById(request.getId());
		if(!carModel.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getCarModelRepo().delete(carModel.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addFuelType(SoapAddFuelTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		FuelTypeDbModel fuelType = new FuelTypeDbModel();
		
		fuelType.setName(request.getName());
		unitOfWork.getFuelTypeRepo().save(fuelType);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteFuelType(SoapDeleteFuelTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<FuelTypeDbModel> fuelType = unitOfWork.getFuelTypeRepo().findById(request.getId());
		if(!fuelType.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getFuelTypeRepo().delete(fuelType.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addTransmissionType(SoapAddTransmissionTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		TransmissionTypeDbModel transmissionType = new TransmissionTypeDbModel();
		
		transmissionType.setName(request.getName());
		unitOfWork.getTransmissionTypeRepo().save(transmissionType);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteTransmissionType(SoapDeleteTransmissionTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<TransmissionTypeDbModel> transmissionType = unitOfWork.getTransmissionTypeRepo().findById(request.getId());
		if(!transmissionType.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getTransmissionTypeRepo().delete(transmissionType.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
}
