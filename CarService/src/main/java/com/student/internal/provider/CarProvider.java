package com.student.internal.provider;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarClassDbModel;
import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.dal.CarModelDbModel;
import com.student.data.dal.FuelTypeDbModel;
import com.student.data.dal.LocationDbModel;
import com.student.data.dal.PublisherTypeDbModel;
import com.student.data.dal.TransmissionTypeDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.jwt.Permission;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;
import com.student.soap.carservice.contract.Car;
import com.student.soap.carservice.contract.SoapAddCarRequest;
import com.student.soap.carservice.contract.SoapAddCarResponse;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;
import com.student.soap.carservice.contract.SoapCarsResponse;
import com.student.soap.carservice.contract.SoapDeactivatePublisherRequest;
import com.student.soap.carservice.contract.SoapResponse;
import com.student.soap.carservice.contract.SoapSearchCarsRequest;
import com.student.soap.resource.client.AgentServiceClient;
import com.student.soap.resource.client.ScheduleServiceClient;
import com.student.soap.resource.client.UserServiceClient;
import com.student.soap.scheduleservice.contract.SoapCarPriceResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.scheduleservice.contract.SoapIntegerResponse;
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
	
	private boolean authanticated(AuthenticationTokenParseResult token, Permission permission) {
		if (!token.isValid() || permission == null)
		{			
			return false;
		}
		
		boolean hasUserPermission = token.getRoleName().equals("BASIC") && permission.getResourceId() == token.getUserId() && permission.getResourceTypeId() == 1;
		boolean hasPublisherPermission = token.getRoleName().equals("AGENT") && permission.getResourceId()!=null && permission.getResourceTypeId() == 2;
		
		return hasUserPermission || hasPublisherPermission;
	}

	public SoapAddCarResponse addCar(SoapAddCarRequest request) {
		SoapAddCarResponse response = new SoapAddCarResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == 1 ).findFirst().orElse(null);
		
		if(!authanticated(token, requiredPermission)){
			response.setAuthorized(false);
			return response;
		}
		
		int publisherId = requiredPermission.getResourceId();
		int publisherTypeId =  token.getRoleId();

		response.setAuthorized(true);

		// If the publisher is a user, he can't have more than 3 cars
		if (token.getRoleId() == 1) {
			List<CarDbModel> cars = unitOfWork.getCarRepo()
					.findByPublisherIdAndPublisherTypeId(publisherId, publisherTypeId);
			if (cars.size() >= 3) {
				response.setSuccess(false);
				return response;
			}
		}

		CarDbModel car = new CarDbModel();
		
		Optional<LocationDbModel> location = unitOfWork.getLocationRepo().findById(request.getLocationId());
		if (!location.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setLocation(location.get());
		
		Optional<CarClassDbModel> carClass = unitOfWork.getCarClassRepo().findById(request.getCarClassId());
		if (!carClass.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setCarClass(carClass.get());
		
		Optional<CarModelDbModel> model =  unitOfWork.getCarModelRepo().findById(request.getModelId());
		if(!model.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setCarModel(model.get());
		
		Optional<FuelTypeDbModel> fuelType = unitOfWork.getFuelTypeRepo().findById(request.getFuelTypeId());
		if(!fuelType.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setFuelType(fuelType.get());
		
		Optional<TransmissionTypeDbModel> transmissionType = unitOfWork.getTransmissionTypeRepo().findById(request.getTransmissionTypeId());
		if(!transmissionType.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setTransmissionType(transmissionType.get());
		
		Optional<PublisherTypeDbModel> publisherType = unitOfWork.getPublisherTypeRepo().findById(publisherTypeId);
		if(!publisherType.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		car.setPublisherType(publisherType.get());
		
		car.setChildSeats(request.getChildSeats());
		car.setMileage(request.getMileage());
		car.setPublisherId(publisherId);
		car.setActive(true);

		unitOfWork.getCarRepo().save(car);
		
		response.setId(car.getId());
		response.setSuccess(true);
		return response;
	}

	public SoapCarsResponse seachCars(SoapSearchCarsRequest request) {
		SoapCarsResponse response = new SoapCarsResponse();

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		long requestMilis = request.getStartDate().toGregorianCalendar().getTimeInMillis();
        long timeDifference = requestMilis - gregorianCalendar.getTimeInMillis();
		if(timeDifference < 172800000 ) {
			return response;
		}
		
		List<CarDbModel> cars = unitOfWork.getCarRepo().findAll().stream()
				.filter(car -> car.getLocation().getId() == request.getLocationId() && car.isActive() == true).collect(Collectors.toList());
		
		if (request.getManufacturerId() != null) {
			cars = cars.stream().filter(car -> car.getCarModel().getCarManufacturer().getId() == request.getManufacturerId())
					.collect(Collectors.toList());
		}
		
		if (request.getModelId() != null) {
			cars = cars.stream().filter(car -> car.getCarModel().getId() == request.getModelId())
					.collect(Collectors.toList());
		}
		
		if (request.getFuelTypeId() != null) {
			cars = cars.stream().filter(car -> car.getFuelType().getId() == request.getFuelTypeId())
					.collect(Collectors.toList());
		}
		
		if (request.getTransmissionTypeId() != null) {
			cars = cars.stream().filter(car -> car.getTransmissionType().getId() == request.getTransmissionTypeId())
					.collect(Collectors.toList());
		}
		
		if (request.getCarClassId() != null) {
			cars = cars.stream().filter(car -> car.getCarClass().getId() == request.getCarClassId())
					.collect(Collectors.toList());
		}
		
		if (request.getMinMileage() != null) {
			cars = cars.stream().filter(car -> car.getMileage() >= request.getMinMileage())
					.collect(Collectors.toList());
		}
		
		if (request.getMaxMileage() != null) {
			cars = cars.stream().filter(car -> car.getMileage() <= request.getMaxMileage())
					.collect(Collectors.toList());
		}
		
		if (request.getMinChildSeats() != null) {
			cars = cars.stream().filter(car -> car.getChildSeats() >= request.getMinChildSeats())
					.collect(Collectors.toList());
		}
		
		if (request.getPublisherTypeId() != null) {
			cars = cars.stream().filter(car -> car.getPublisherType().getId() == request.getPublisherTypeId())
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
					continue;
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
					continue;
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
				continue;
			}
			
			// Fetch comment count
			try {
				SoapIntegerResponse commentCount = scheduleServiceClient.getCarCommentCount(objectIn.getId());
				if (commentCount.isSuccess()) {
					objectOut.setCommentCount(commentCount.getValue());
				}
			} catch (Exception e) {
				System.out.println(e);
				continue;
			}

			// Fetch prices
			if (request.getStartDate() != null && request.getEndDate() != null) {
				try {
					SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(objectIn.getId(),
							request.getStartDate(), request.getEndDate());
					if (carPriceResponse.isSuccess()) {
						
						//Does car have warranty if it's requested?
						if (request.isCollisionWarranty() != null && request.isCollisionWarranty() && carPriceResponse.getCollisionWarranty() == null) {
							continue;
						}
						
						//Does total price fit in in price range if requested?
						//MIN PRICE
						if (request.getMinPrice() != null && carPriceResponse.getTotalPrice() < request.getMinPrice()) {
							continue;
						}

						//MAX PRICE
						if (request.getMaxPrice() != null && carPriceResponse.getTotalPrice() > request.getMaxPrice()) {
							continue;
						}
						
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
					continue;
				}
			}
			
			response.getCar().add(objectOut);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapCarResponse getCar(SoapCarRequest request) {
		SoapCarResponse response = new SoapCarResponse();
		response.setCar(new Car());
		
		boolean priceRequired = request.getStartDate()!= null && request.getEndDate()!= null;

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getId());
		if (!car.isPresent() || !car.get().isActive()) {
			response.setSuccess(false);
			return response;
		}
		
		if(priceRequired){
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			long requestMilis = request.getStartDate().toGregorianCalendar().getTimeInMillis();
	        long timeDifference = requestMilis - gregorianCalendar.getTimeInMillis();
			if(timeDifference < 172800000 ) {
				return response;
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
		}

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

		// Fetch images
		for (CarImageDbModel image : car.get().getImages()) {
			response.getCar().getImage().add(image.getId());
		}

		// Fetch comment count
		try {
			SoapIntegerResponse commentCount = scheduleServiceClient.getCarCommentCount(request.getId());
			if (commentCount.isSuccess()) {
				response.getCar().setCommentCount(commentCount.getValue());
			}
		} catch (Exception e) {
			System.out.println(e);
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
}
