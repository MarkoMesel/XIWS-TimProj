package com.student.internal.provider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.jwt.Permission;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;
import com.student.soap.carservice.contract.Car;
import com.student.soap.carservice.contract.SoapCarsResponse;
import com.student.soap.carservice.contract.SoapGetTopCarsByCommentCountRequest;
import com.student.soap.carservice.contract.SoapGetTopCarsByMileageRequest;
import com.student.soap.carservice.contract.SoapGetTopCarsByRatingRequest;
import com.student.soap.resource.client.AgentServiceClient;
import com.student.soap.resource.client.ScheduleServiceClient;
import com.student.soap.resource.client.UserServiceClient;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.scheduleservice.contract.SoapIntegerResponse;
import com.student.soap.userservice.contract.SoapGetResponse;

@Component("StatisticsProvider")
public class StatisticsProvider {

	private UnitOfWork unitOfWork;
	private ScheduleServiceClient scheduleServiceClient;
	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;
	private JwtUtil jwtUtil;

	@Autowired
	public StatisticsProvider(UnitOfWork unitOfWork, ScheduleServiceClient scheduleServiceClient,
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
		
		return token.getRoleName().equals("AGENT") && permission.getResourceId()!=null && permission.getResourceTypeId() == 2;
	}

	public SoapCarsResponse getByMileage(SoapGetTopCarsByMileageRequest request) {
		SoapCarsResponse response = new SoapCarsResponse();
		
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
		
		List<CarDbModel> cars = unitOfWork.getCarRepo()
				.findAll()
				.stream()
				.filter(x->x.getPublisherId() == publisherId && x.getPublisherType().getId() == publisherTypeId)
				.sorted(Comparator.comparingInt(CarDbModel::getMileage).reversed())
				.collect(Collectors.toList());
		
		
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
			
			response.getCar().add(objectOut);
		}

		response.setSuccess(true);
		
		return response;
	}

	public SoapCarsResponse getByComments(SoapGetTopCarsByCommentCountRequest request) {	
		SoapCarsResponse response = new SoapCarsResponse();
		List<Car> tempResponse = new ArrayList<>();
		
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
		
		List<CarDbModel> cars = unitOfWork.getCarRepo()
				.findAll()
				.stream()
				.filter(x->x.getPublisherId() == publisherId && x.getPublisherType().getId() == publisherTypeId)
				.collect(Collectors.toList());
		
		
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
			
			tempResponse.add(objectOut);
		}
		
		tempResponse = tempResponse.stream().sorted(Comparator.comparingInt(Car::getCommentCount).reversed())
				.collect(Collectors.toList());
		
		response.getCar().addAll(tempResponse);
		
		response.setSuccess(true);
		
		return response;
	}

	public SoapCarsResponse getByRating(SoapGetTopCarsByRatingRequest request) {
		SoapCarsResponse response = new SoapCarsResponse();
		List<Car> tempResponse = new ArrayList<>();
		
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
		
		List<CarDbModel> cars = unitOfWork.getCarRepo()
				.findAll()
				.stream()
				.filter(x->x.getPublisherId() == publisherId && x.getPublisherType().getId() == publisherTypeId)
				.collect(Collectors.toList());
		
		
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
			
			tempResponse.add(objectOut);
		}
		
		tempResponse = tempResponse.stream().sorted(Comparator.comparingInt(Car::getRating).reversed())
				.collect(Collectors.toList());
		
		response.getCar().addAll(tempResponse);
		
		response.setSuccess(true);
		
		return response;
	}
}
