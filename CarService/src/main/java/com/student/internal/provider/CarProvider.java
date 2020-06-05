package com.student.internal.provider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarClassDbModel;
import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.dal.CarManufacturerDbModel;
import com.student.data.dal.CarModelDbModel;
import com.student.data.dal.FuelTypeDbModel;
import com.student.data.dal.LocationDbModel;
import com.student.data.dal.TransmissionTypeDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;
import com.student.soap.carservice.contract.Car;
import com.student.soap.carservice.contract.NamedObject;
import com.student.soap.carservice.contract.SoapAddCarClassRequest;
import com.student.soap.carservice.contract.SoapAddCarModelRequest;
import com.student.soap.carservice.contract.SoapAddCarRequest;
import com.student.soap.carservice.contract.SoapAddFuelTypeRequest;
import com.student.soap.carservice.contract.SoapAddLocationRequest;
import com.student.soap.carservice.contract.SoapAddManufacturerRequest;
import com.student.soap.carservice.contract.SoapAddTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;
import com.student.soap.carservice.contract.SoapDeactivatePublisherRequest;
import com.student.soap.carservice.contract.SoapDeleteCarClassRequest;
import com.student.soap.carservice.contract.SoapDeleteCarModelRequest;
import com.student.soap.carservice.contract.SoapDeleteFuelTypeRequest;
import com.student.soap.carservice.contract.SoapDeleteImageRequest;
import com.student.soap.carservice.contract.SoapDeleteLocationRequest;
import com.student.soap.carservice.contract.SoapDeleteManufacturerRequest;
import com.student.soap.carservice.contract.SoapDeleteTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapGetImageRequest;
import com.student.soap.carservice.contract.SoapGetImageResponse;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapPostImageRequest;
import com.student.soap.carservice.contract.SoapPostImageResponse;
import com.student.soap.carservice.contract.SoapResponse;
import com.student.soap.carservice.contract.SoapSearchCarsRequest;
import com.student.soap.carservice.contract.SoapSearchCarsResponse;
import com.student.soap.resource.client.AgentServiceClient;
import com.student.soap.resource.client.ScheduleServiceClient;
import com.student.soap.resource.client.UserServiceClient;
import com.student.soap.scheduleservice.contract.SoapCarPriceResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.userservice.contract.SoapGetResponse;

@Component("CarProvider")
public class CarProvider {

	private UnitOfWork unitOfWork;
	private ScheduleServiceClient scheduleServiceClient;
	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;
	private JwtUtil jwtUtil;

	@Autowired
	public CarProvider(UnitOfWork unitOfWork, ScheduleServiceClient scheduleServiceClient,
			UserServiceClient userServiceClient, AgentServiceClient agentServiceClient, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.scheduleServiceClient = scheduleServiceClient;
		this.userServiceClient = userServiceClient;
		this.agentServiceClient = agentServiceClient;
		this.jwtUtil = jwtUtil;
	}

	public SoapResponse addCar(SoapAddCarRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(token, 1, request.getPublisherId(), request.getPublisherTypeId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		// If the publisher is a user, he can't have more than 3 cars
		if (token.getRoleId() == 1) {
			List<CarDbModel> cars = unitOfWork.getCarRepo()
					.findByPublisherIdAndPublisherTypeId(request.getPublisherId(), request.getPublisherTypeId());
			if (cars.size() >= 3) {
				response.setSuccess(false);
				return response;
			}
		}

		CarDbModel car = new CarDbModel();
		car.setCarClass(unitOfWork.getCarClassRepo().findById(request.getCarClassId()).get());
		car.setCarModel(unitOfWork.getCarModelRepo().findById(request.getModelId()).get());
		car.setChildSeats(request.getChildSeats());
		car.setFuelType(unitOfWork.getFuelTypeRepo().findById(request.getFuelTypeId()).get());
		car.setMileage(request.getMileage());
		car.setTransmissionType(unitOfWork.getTransmissionTypeRepo().findById(request.getTransmissionTypeId()).get());
		car.setPublisherType(unitOfWork.getPublisherTypeRepo().findById(request.getPublisherTypeId()).get());
		car.setPublisherId(request.getPublisherId());

		unitOfWork.getCarRepo().save(car);

		response.setSuccess(true);
		return response;
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

	public SoapSearchCarsResponse seachCars(SoapSearchCarsRequest request) {
		SoapSearchCarsResponse response = new SoapSearchCarsResponse();

		List<CarDbModel> cars = unitOfWork.getCarRepo().findAll().stream()
				.filter(car -> car.getLocation().getId() == request.getLocationId()).collect(Collectors.toList());

		if (request.getCarClassId() != null) {
			cars = cars.stream().filter(car -> car.getCarClass().getId() == request.getCarClassId())
					.collect(Collectors.toList());
		}

		for (CarDbModel objectIn : cars) {
			Car objectOut = new Car();

			objectOut.setId(objectIn.getId());
			objectOut.setLocationId(objectIn.getLocation().getId());
			objectOut.setLocationName(objectIn.getLocation().getName());
			objectOut.setModelId(objectIn.getCarModel().getId());
			objectOut.setModelName(objectIn.getCarModel().getName());
			objectOut.setManufacturerId(objectIn.getCarModel().getCarManufacturer().getId());
			objectOut.setManufacturerName(objectIn.getCarModel().getCarManufacturer().getName());
			objectOut.setFuelTypeName(objectIn.getFuelType().getName());
			objectOut.setFuelTypeId(objectIn.getFuelType().getId());
			objectOut.setTransmissionTypeName(objectIn.getTransmissionType().getName());
			objectOut.setTransmissionTypeId(objectIn.getTransmissionType().getId());
			objectOut.setCarClassName(objectIn.getCarClass().getName());
			objectOut.setCarClassId(objectIn.getCarClass().getId());
			objectOut.setMileage(objectIn.getMileage());
			objectOut.setChildSeats(objectIn.getChildSeats());
			objectOut.setPublisherId(objectIn.getPublisherId());
			objectOut.setPublisherTypeId(objectIn.getPublisherType().getId());
			objectOut.setPublisherTypeName(objectIn.getPublisherType().getName());
			
			//Fetch publisher name
			if(objectIn.getPublisherType().getName().equals("USER")) {
				try {
					SoapGetResponse userResponse = userServiceClient.getUser(objectIn.getPublisherId());
					if(userResponse.isSuccess()) {
						objectOut.setPublisherName(userResponse.getFirstName()+" "+userResponse.getLastName());					
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			
			if(objectIn.getPublisherType().getName().equals("AGENT")) {
				try {
					SoapAgentByIdResponse agentResponse = agentServiceClient.getAgent(objectIn.getPublisherId());
					if(agentResponse.isSuccess()) {
						objectOut.setPublisherName(agentResponse.getName());					
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			
			//Fetch images
			objectIn.getImages().forEach(image -> {
				objectOut.getImage().add(image.getId());
			});

			// Fetch rating
			try {
				SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(objectIn.getId());
				if (carRatingResponse.isSuccess()) {
					objectOut.setRating(carRatingResponse.getRating());
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			// Fetch prices
			if (request.getStartDate() != null && request.getEndDate() != null) {
				try {
					SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(objectIn.getId(),
							request.getStartDate(), request.getEndDate());
					if (carPriceResponse.isSuccess()) {
						objectOut.setCollisionWaranty(carPriceResponse.getCollisionWarranty());
						objectOut.setMileagePenalty(carPriceResponse.getMileagePenalty());
						objectOut.setMileageThreshold(carPriceResponse.getMileageThreshold());
						objectOut.setPrice(carPriceResponse.getPrice());
						objectOut.setDiscount(carPriceResponse.getDiscount());
						objectOut.setTotalPrice(carPriceResponse.getTotalPrice());

						// Calculate penalty
						if (objectOut.getMileagePenalty() != null && objectOut.getMileageThreshold() != null
								&& request.getPlannedMileage() != null
								&& request.getPlannedMileage() > objectOut.getMileageThreshold()) {
							int penalty = (request.getPlannedMileage() - objectOut.getMileageThreshold())
									* objectOut.getMileagePenalty();
							objectOut.setEstimatedPenaltyPrice(penalty);
						} else {
							objectOut.setEstimatedPenaltyPrice(0);
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			
			response.getCar().add(objectOut);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapCarResponse getCar(SoapCarRequest request) {
		SoapCarResponse response = new SoapCarResponse();

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getId());
		if (!car.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		response.setCar(new Car());

		response.getCar().setId(car.get().getId());
		response.getCar().setLocationId(car.get().getLocation().getId());
		response.getCar().setLocationName(car.get().getLocation().getName());
		response.getCar().setModelId(car.get().getCarModel().getId());
		response.getCar().setModelName(car.get().getCarModel().getName());
		response.getCar().setManufacturerId(car.get().getCarModel().getCarManufacturer().getId());
		response.getCar().setManufacturerName(car.get().getCarModel().getCarManufacturer().getName());
		response.getCar().setFuelTypeName(car.get().getFuelType().getName());
		response.getCar().setFuelTypeId(car.get().getFuelType().getId());
		response.getCar().setTransmissionTypeName(car.get().getTransmissionType().getName());
		response.getCar().setTransmissionTypeId(car.get().getTransmissionType().getId());
		response.getCar().setCarClassName(car.get().getCarClass().getName());
		response.getCar().setCarClassId(car.get().getCarClass().getId());
		response.getCar().setMileage(car.get().getMileage());
		response.getCar().setChildSeats(car.get().getChildSeats());
		response.getCar().setPublisherId(car.get().getPublisherId());
		response.getCar().setPublisherTypeId(car.get().getPublisherType().getId());
		response.getCar().setPublisherTypeName(car.get().getPublisherType().getName());
		
		//Fetch publisher name
		if(car.get().getPublisherType().getName().equals("USER")) {
			try {
				SoapGetResponse userResponse = userServiceClient.getUser(car.get().getPublisherId());
				if(userResponse.isSuccess()){
					response.getCar().setPublisherName(userResponse.getFirstName()+" "+userResponse.getLastName());
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		if(car.get().getPublisherType().getName().equals("AGENT")) {
			try {
				SoapAgentByIdResponse agentResponse = agentServiceClient.getAgent(car.get().getPublisherId());
				if(agentResponse.isSuccess()) {
					response.getCar().setPublisherName(agentResponse.getName());	
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Fetch rating
		try {
			SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(request.getId());
			if (carRatingResponse.isSuccess()) {
				response.getCar().setRating(carRatingResponse.getRating());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// Fetch prices
		if (request.getStartDate() != null && request.getEndDate() != null) {
			try {
				SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(request.getId(),
						request.getStartDate(), request.getEndDate());
				if (carPriceResponse.isSuccess()) {
					response.getCar().setCollisionWaranty(carPriceResponse.getCollisionWarranty());
					response.getCar().setMileagePenalty(carPriceResponse.getMileagePenalty());
					response.getCar().setMileageThreshold(carPriceResponse.getMileageThreshold());
					response.getCar().setPrice(carPriceResponse.getPrice());
					response.getCar().setDiscount(carPriceResponse.getDiscount());
					response.getCar().setTotalPrice(carPriceResponse.getTotalPrice());

					// Calculate penalty
					if (response.getCar().getMileagePenalty() != null && response.getCar().getMileageThreshold() != null
							&& request.getPlannedMileage() != null
							&& request.getPlannedMileage() > response.getCar().getMileageThreshold()) {
						int penalty = (request.getPlannedMileage() - response.getCar().getMileageThreshold())
								* response.getCar().getMileagePenalty();
						response.getCar().setEstimatedPenaltyPrice(penalty);
					} else {
						response.getCar().setEstimatedPenaltyPrice(0);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Fetch images
		for (CarImageDbModel image : car.get().getImages()) {
			response.getCar().getImage().add(image.getId());
		}

		response.setSuccess(true);
		return response;
	}

	public SoapPostImageResponse postCarImage(SoapPostImageRequest request) {
		SoapPostImageResponse response = new SoapPostImageResponse();

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getCarId());
		if (!car.isPresent()) {
			return new SoapPostImageResponse();
		}

		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, car.get().getId(), car.get().getPublisherType().getId())) {
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
		if (!image.isPresent()) {
			return new SoapResponse();
		}

		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, image.get().getCar().getId(),
				image.get().getCar().getPublisherType().getId())) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<CarClassDbModel> carClass = unitOfWork.getCarClassRepo().findById(request.getId());
		if (!carClass.isPresent()) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo().findById(request.getId());
		if (!manufacturer.isPresent()) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo()
				.findById(request.getManufacturerId());
		if (!manufacturer.isPresent()) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<CarModelDbModel> carModel = unitOfWork.getCarModelRepo().findById(request.getId());
		if (!carModel.isPresent()) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<FuelTypeDbModel> fuelType = unitOfWork.getFuelTypeRepo().findById(request.getId());
		if (!fuelType.isPresent()) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
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
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<TransmissionTypeDbModel> transmissionType = unitOfWork.getTransmissionTypeRepo()
				.findById(request.getId());
		if (!transmissionType.isPresent()) {
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

	public SoapResponse deactivatePublisher(SoapDeactivatePublisherRequest request) {
		SoapResponse response = new SoapResponse();

		List<CarDbModel> cars = unitOfWork.getCarRepo().findByPublisherIdAndPublisherTypeId(request.getPublisherId(),
				request.getPublisherTypeId());

		for (CarDbModel car : cars) {
			car.setActive(false);
			unitOfWork.getCarRepo().save(car);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapNamedObjectsResponse getAllLocations() {
		SoapNamedObjectsResponse response = new SoapNamedObjectsResponse();

		unitOfWork.getLocationRepo().findAll().forEach(objectIn -> {
			NamedObject objectOut = new NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.getObject().add(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public SoapResponse addLocation(SoapAddLocationRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		LocationDbModel fuelType = new LocationDbModel();

		fuelType.setName(request.getName());
		unitOfWork.getLocationRepo().save(fuelType);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteLocation(SoapDeleteLocationRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		Optional<LocationDbModel> fuelType = unitOfWork.getLocationRepo().findById(request.getId());
		if (!fuelType.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getLocationRepo().delete(fuelType.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
