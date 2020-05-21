package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalAddCarRequest;
import com.student.internal.contract.InternalAutherisedResponse;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalCarResponse;
import com.student.internal.contract.InternalCarsResponse;
import com.student.internal.contract.InternalImageResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.jwt.Permission;
import com.student.soap.resource.client.ScheduleServiceClient;
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
		AuthenticationTokenParseResult tokenParseResult =  jwtUtil.parseAuthenticationToken(request.getToken());
		Permission addCarPermission = tokenParseResult.getPermissions().stream().filter(permission -> permission.getPermissionId() == 1).findFirst().orElse(null);
		if(
				!tokenParseResult.isValid() 
				|| addCarPermission == null
				|| ( !tokenParseResult.getRoleName().equals("ADMIN")
				&& !(!tokenParseResult.getRoleName().equals("AGENT") || (addCarPermission!=null && request.getAgentId() != null))
				&& !(!tokenParseResult.getRoleName().equals("USER") || (addCarPermission!=null)))
				) {
			response.setAutherised(false);
			return response;
		}
		
		response.setAutherised(true);
		
		CarDbModel car = new CarDbModel();
		car.setCarClass(unitOfWork.getCarClassRepo().findById(request.getCarClassId()).get());
		car.setCarModel(unitOfWork.getCarModelRepo().findById(request.getModelId()).get());
		car.setChildSeats(request.getChildSeats());
		car.setFuelType(unitOfWork.getFuelTypeRepo().findById(request.getFuelTypeId()).get());
		car.setMileage(request.getMileage());
		car.setTransmissionType(unitOfWork.getTransmissionTypeRepo().findById(request.getTransmissionTypeId()).get());
		car.setPublisherType(unitOfWork.getPublisherTypeRepo().findById(addCarPermission.getResourceTypeId()).get());
		car.setPublisherId(addCarPermission.getResourceId());
		
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

	public InternalImageResponse getCarImage(int id) {
		InternalImageResponse response = new InternalImageResponse();

		Optional<CarImageDbModel> image = unitOfWork.getCarImageRepo().findById(id);
		if (!image.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		response.setImage(image.get().getImage());
		response.setSuccess(true);
		return response;
	}

	public InternalCarsResponse getAllCars() {
		InternalCarsResponse response = new InternalCarsResponse();
		
		for(CarDbModel objectIn:unitOfWork.getCarRepo().findAll())
		{
			InternalCarsResponse.Car objectOut= new InternalCarsResponse.Car();
			
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
			objectIn.getImages().forEach(image ->{
			objectOut.getCarImages().add(image.getId());
			});
			
			SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(objectIn.getId());
			if(!carRatingResponse.isSuccess()) {
				response = new InternalCarsResponse();
				response.setSuccess(false);
				return response;
			}
			objectOut.setCarRating(carRatingResponse.getRating());
			
			//TODO: search - scheduleServiceClient.getCarPrice
			/*
			SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(objectIn.getId(), );
			if(!carRatingResponse.isSuccess()) {
				response = new InternalCarsResponse();
				response.setSuccess(false);
				return response;
			}
			objectOut.setPricePerDay(carPriceResponse.getPricePerDay());
			objectOut.setCollisionWaranty(carPriceResponse.isCollisionWarranty());
			objectOut.setMileageThreshold(carPriceResponse.getMileageThreshold());
			objectOut.setMileagePenalty(carPriceResponse.getMileageThreshold());
			*/
			
			response.addCar(objectOut);
		}
		
		response.setSuccess(true);
		return response;
	}

	public InternalCarResponse getCar(int id) {
		InternalCarResponse response = new InternalCarResponse();

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(id);
		if (!car.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		response.setId(car.get().getId());
		response.setModelId(car.get().getCarModel().getId());
		response.setModelName(car.get().getCarModel().getName());
		response.setManufacturerId(car.get().getCarModel().getCarManufacturer().getId());
		response.setManufacturerName(car.get().getCarModel().getCarManufacturer().getName());
		response.setFuelType(car.get().getFuelType().getName());
		response.setFuelTypeId(car.get().getFuelType().getId());
		response.setTransmission(car.get().getTransmissionType().getName());
		response.setTransmissionTypeId(car.get().getTransmissionType().getId());
		response.setCarClass(car.get().getCarClass().getName());
		response.setCarClassId(car.get().getCarClass().getId());
		response.setMileage(car.get().getMileage());
		response.setChildSeats(car.get().getChildSeats());
		response.setAgentId(23);
		response.setAgentName("TODO");

		SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(id);
		if (!carRatingResponse.isSuccess()) {
			response = new InternalCarResponse();
			response.setSuccess(false);
			return response;
		}
		response.setCarRating(carRatingResponse.getRating());
		
		//TODO: getCar - scheduleServiceClient.getCarPrice
		/*
		SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(objectIn.getId(), );
		if(!carRatingResponse.isSuccess()) {
			response = new InternalCarsResponse();
			response.setSuccess(false);
			return response;
		}
		objectOut.setPricePerDay(carPriceResponse.getPricePerDay());
		objectOut.setCollisionWaranty(carPriceResponse.isCollisionWarranty());
		objectOut.setMileageThreshold(carPriceResponse.getMileageThreshold());
		objectOut.setMileagePenalty(carPriceResponse.getMileageThreshold());
		*/

		for (CarImageDbModel image : car.get().getImages()) {
			response.getCarImages().add(image.getId());
		}

		response.setSuccess(true);
		return response;
	}
}
