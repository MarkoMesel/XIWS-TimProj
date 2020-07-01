package com.xiws.agentm.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.student.agentservice.data.dal.AgentDbModel;
import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.dal.LocationDbModel;
import com.student.http.contract.HttpAddCarModel;
import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpCreateNamedObject;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.http.contract.HttpSearchCarsRequest;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.internal.translator.Translator;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;
import com.student.soap.carservice.contract.Car;
import com.student.soap.carservice.contract.NamedObject;
import com.student.soap.client.CarServiceClient;
import com.student.soap.contract.carservice.SoapAddCarClassRequest;
import com.student.soap.contract.carservice.SoapAddCarModelRequest;
import com.student.soap.contract.carservice.SoapAddCarResponse;
import com.student.soap.contract.carservice.SoapAddFuelTypeRequest;
import com.student.soap.contract.carservice.SoapAddLocationRequest;
import com.student.soap.contract.carservice.SoapAddManufacturerRequest;
import com.student.soap.contract.carservice.SoapAddTransmissionTypeRequest;
import com.student.soap.contract.carservice.SoapAllCarModelsRequest;
import com.student.soap.contract.carservice.SoapCarClassesRequest;
import com.student.soap.contract.carservice.SoapCarManufacturersRequest;
import com.student.soap.contract.carservice.SoapCarModelsByManufacturerRequest;
import com.student.soap.contract.carservice.SoapCarModelsResponse;
import com.student.soap.contract.carservice.SoapCarResponse;
import com.student.soap.contract.carservice.SoapDeleteCarClassRequest;
import com.student.soap.contract.carservice.SoapDeleteCarModelRequest;
import com.student.soap.contract.carservice.SoapDeleteFuelTypeRequest;
import com.student.soap.contract.carservice.SoapDeleteImageRequest;
import com.student.soap.contract.carservice.SoapDeleteLocationRequest;
import com.student.soap.contract.carservice.SoapDeleteManufacturerRequest;
import com.student.soap.contract.carservice.SoapDeleteTransmissionTypeRequest;
import com.student.soap.contract.carservice.SoapFuelTypesRequest;
import com.student.soap.contract.carservice.SoapGetImageRequest;
import com.student.soap.contract.carservice.SoapGetImageResponse;
import com.student.soap.contract.carservice.SoapLocationsRequest;
import com.student.soap.contract.carservice.SoapNamedObjectsResponse;
import com.student.soap.contract.carservice.SoapPostImageRequest;
import com.student.soap.contract.carservice.SoapPostImageResponse;
import com.student.soap.contract.carservice.SoapResponse;
import com.student.soap.contract.carservice.SoapSearchCarsResponse;
import com.student.soap.contract.carservice.SoapTransmissionTypesRequest;
import com.student.soap.scheduleservice.contract.SoapCarPriceResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.userservice.contract.SoapGetResponse;
import com.xiws.agentm.repocar.CarClassRepo;
import com.xiws.agentm.repocar.CarManufacturerRepo;
import com.xiws.agentm.repocar.CarModelRepo;
import com.xiws.agentm.repocar.CarRepo;
import com.xiws.agentm.repocar.FuelTypeRepo;
import com.xiws.agentm.repocar.LocationRepo;
import com.xiws.agentm.repocar.TransmissionTypeRepo;
import com.xiws.agentm.reposchedule.CarPriceListRepo;
import com.xiws.agentm.reposchedule.ReservationRepo;
import com.xiws.agentm.request.AddLocationRequestModel;
import com.xiws.agentm.request.CarRequestModel;
import com.xiws.agentm.response.CarAndManufacturerResponseModel;
import com.xiws.agentm.response.CarPriceResponseModel;
import com.xiws.agentm.response.CarResponseModel;
import com.xiws.agentm.response.NamedObjectResponseModel;
import com.xiws.agentm.dalcar.LocationDbModel;
import com.xiws.agentm.dalcar.CarDbModel;
import com.xiws.agentm.dalschedule.CarPriceListDbModel;
import com.xiws.agentm.dalschedule.PriceDbModel;
import com.xiws.agentm.repoagent.AgentRepo;
import com.xiws.agentm.dalcar.CarImageDbModel;
import com.xiws.agentm.dalagent.AgentDbModel;
import com.xiws.agentm.dalschedule.ReservationDbModel;


@Controller
public class CarController {
	
	@Autowired
	LocationRepo locationRepo;
	
	@Autowired
	CarModelRepo carModelRepo;
	
	@Autowired
	CarManufacturerRepo carManufacturerRepo;
	
	@Autowired
	FuelTypeRepo fuelTypeRepo;
	
	@Autowired
	TransmissionTypeRepo transmissionTypeRepo;
	
	@Autowired
	CarClassRepo carClassRepo;
	
	@Autowired
	CarRepo	carRepo;
	
	@Autowired
	CarPriceListRepo carPriceListRepo;
	
	@Autowired
	AgentRepo agentRepo;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	private Translator translator;
	private CarServiceClient carServiceClient;

	@Autowired
	public CarController(CarServiceClient carServiceClient, Translator translator) {
		this.carServiceClient = carServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/locations")
	public ResponseEntity<List<NamedObjectResponseModel>> getAllLocations() {
		List<NamedObjectResponseModel> response = new ArrayList<NamedObjectResponseModel>();
		locationRepo.findAll().forEach(objectIn -> {
			NamedObjectResponseModel responseModel = new NamedObjectResponseModel(
					objectIn.getId(),
					objectIn.getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
			/*
			NamedObject objectOut = new NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.getObject().add(objectOut);
			*/
		/*
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapLocationsRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/locations")
	public ResponseEntity<?> addLocation(@RequestBody AddLocationRequestModel request/*,
			@RequestHeader("token") String token*/) {
		LocationDbModel location = new LocationDbModel();
		
		location.setName(request.getName());
		locationRepo.save(location);
		return new ResponseEntity<>(HttpStatus.OK);
		/*
		SoapAddLocationRequest internalRequest = new SoapAddLocationRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
		/*
		SoapAddLocationRequest internalRequest = new SoapAddLocationRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		//unitOfWork.getLocationRepo().save(location);

		//response.setSuccess(true);
		//return response;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/locations/{id}")
	public ResponseEntity<?> deleteLocation(/*@RequestHeader("token") String token,*/@PathVariable int id) {
		LocationDbModel location = locationRepo.findById(id).get();
		try {
			locationRepo.delete(location);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		/*
		SoapDeleteLocationRequest internalRequest = new SoapDeleteLocationRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);

		Optional<LocationDbModel> location = unitOfWork.getLocationRepo().findById(request.getId());
		if (!location.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getLocationRepo().delete(location.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/models")
	public ResponseEntity<List<CarAndManufacturerResponseModel>> getAllCarModels() {
		List<CarAndManufacturerResponseModel> response = new ArrayList<CarAndManufacturerResponseModel>();
		
		carModelRepo.findAll().forEach(objectIn -> {
			CarAndManufacturerResponseModel responseModel = new CarAndManufacturerResponseModel(
					objectIn.getId(),
					objectIn.getName(),
					objectIn.getCarManufacturer().getId(),
					objectIn.getCarManufacturer().getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapCarModelsResponse internalResponse = carServiceClient.send(new SoapAllCarModelsRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
		/*
		InternalCarModelsResponse response = new InternalCarModelsResponse();
		unitOfWork.getCarModelRepo().findAll().forEach(carModel -> {
			response.addCarModel(new InternalCarModelsResponse.CarModel(carModel.getId(), carModel.getName(),
					carModel.getCarManufacturer().getId(), carModel.getCarManufacturer().getName()));
		});

		response.setSuccess(true);
		return response;
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/manufacturer/{manufacturerId}/models")
	public ResponseEntity<List<CarAndManufacturerResponseModel>> getCarModelsByManufacturerId(
			@PathVariable("manufacturerId") int manufacturerId) {
		List<CarAndManufacturerResponseModel> response = new ArrayList<CarAndManufacturerResponseModel>();
		carModelRepo.findByManufacturerId(manufacturerId).forEach(objectIn -> {
			CarAndManufacturerResponseModel responseModel = new CarAndManufacturerResponseModel(
					objectIn.getId(),
					objectIn.getName(),
					objectIn.getCarManufacturer().getId(),
					objectIn.getCarManufacturer().getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapCarModelsByManufacturerRequest request = new SoapCarModelsByManufacturerRequest();
		request.setManufacturerId(manufacturerId);
		SoapCarModelsResponse internalResponse = carServiceClient.send(request);
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
		/*
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
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/manufacturers")
	public ResponseEntity<List<NamedObjectResponseModel>> getCarManufacturers() {
		List<NamedObjectResponseModel> response = new ArrayList<NamedObjectResponseModel>();
		carManufacturerRepo.findAll().forEach(objectIn -> {
			NamedObjectResponseModel responseModel = new NamedObjectResponseModel(
					objectIn.getId(),
					objectIn.getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapCarManufacturersRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
		/*
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getCarManufacturerRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/fuelTypes")
	public ResponseEntity<List<NamedObjectResponseModel>> getFuelTypes() {
		List<NamedObjectResponseModel> response = new ArrayList<NamedObjectResponseModel>();
		fuelTypeRepo.findAll().forEach(objectIn -> {
			NamedObjectResponseModel responseModel = new NamedObjectResponseModel(
					objectIn.getId(),
					objectIn.getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapFuelTypesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
		/*
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getFuelTypeRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/transmissionTypes")
	public ResponseEntity<List<NamedObjectResponseModel>> getTransmissionTypes() {
		List<NamedObjectResponseModel> response = new ArrayList<NamedObjectResponseModel>();
		transmissionTypeRepo.findAll().forEach(objectIn -> {
			NamedObjectResponseModel responseModel = new NamedObjectResponseModel(
					objectIn.getId(),
					objectIn.getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapTransmissionTypesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/classes")
	public ResponseEntity<List<NamedObjectResponseModel>> getCarClasses() {
		List<NamedObjectResponseModel> response = new ArrayList<NamedObjectResponseModel>();
		carClassRepo.findAll().forEach(objectIn -> {
			NamedObjectResponseModel responseModel = new NamedObjectResponseModel(
					objectIn.getId(),
					objectIn.getName());
			response.add(responseModel);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
		/*
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapCarClassesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/getCar")
	public ResponseEntity<CarResponseModel> getCar(@RequestBody CarRequestModel request) {
		CarResponseModel response = new CarResponseModel();
		//response.setCar(new Car());
		
		boolean priceRequired = request.getStartDate()!= null && request.getEndDate()!= null;

		Optional<CarDbModel> car = carRepo.findById(request.getId());
		if (!car.isPresent() || !car.get().isActive()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(priceRequired){
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			long requestMilis = request.getStartDate().toGregorianCalendar().getTimeInMillis();
	        long timeDifference = requestMilis - gregorianCalendar.getTimeInMillis();
			if(timeDifference < 172800000 ) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
			// Fetch prices
			if (request.getStartDate() != null && request.getEndDate() != null) {
				try {
					/*
					SoapCarPriceResponse carPriceResponse = scheduleServiceClient.getCarPrice(request.getId(),
							request.getStartDate(), request.getEndDate());
					*/
					////////////////////////////////////////////////////////////////////////
					
					CarPriceResponseModel carPriceResponse = new CarPriceResponseModel();
					CarPriceListDbModel carPricelist = carPriceListRepo.findByCarId(request.getId()).stream()
							.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst().orElse(null);

					/*
					 * TO DO
					if (carPricelist == null) {
						return carPriceResponse;
					}
					*/
					XMLGregorianCalendar gc = request.getStartDate();
					
					List<PriceDbModel> prices = carPricelist.getPriceList().getPrices().stream()
							.filter(price -> price.getStartDate().compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
									&& price.getEndDate().compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0)
							.collect(Collectors.toList());

					int price = 0;
					for (PriceDbModel dailyPrice : prices) {
						price += dailyPrice.getPrice();
					}

					int discount = 0;
					carPriceResponse.setCollisionWarranty(carPricelist.getPriceList().getWarrantyPrice());
					if (request.getStartDate().toGregorianCalendar().getTime().compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 30) {
						discount = price * carPricelist.getPriceList().getDiscountPercentage() / 100;
					}
					carPriceResponse.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					carPriceResponse.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
					carPriceResponse.setPrice(price);
					carPriceResponse.setTotalPrice(price - discount);
					carPriceResponse.setDiscount(discount);
					//carPriceResponse.setSuccess(true);
					//return carPriceResponse;

					////////////////////////////////////////////////////////////////////////
					
					//if (carPriceResponse.isSuccess()) {
					response.setCollisionWaranty(carPriceResponse.getCollisionWarranty());
					response.setMileagePenalty(carPriceResponse.getMileagePenalty());
					response.setMileageThreshold(carPriceResponse.getMileageThreshold());
					response.setPrice(carPriceResponse.getPrice());
					response.setDiscount(carPriceResponse.getDiscount());
					response.setTotalPrice(carPriceResponse.getTotalPrice());

					// Calculate penalty
					if (response.getMileagePenalty() != null && response.getMileageThreshold() != null
							&& request.getPlannedMileage() != null
							&& request.getPlannedMileage() > response.getMileageThreshold()) {
						int penalty = (request.getPlannedMileage() - response.getMileageThreshold())
								* response.getMileagePenalty();
						response.setEstimatedPenalty(penalty);
					} else {
						response.setEstimatedPenalty(0);
					}
					//}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

		response.setId(car.get().getId());
		response.setLocationId(car.get().getLocation().getId());
		response.setLocationName(car.get().getLocation().getName());
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
		response.setPublisherTypeId(car.get().getPublisherType().getId());
		response.setPublisherTypeName(car.get().getPublisherType().getName());
		
		//Fetch publisher name
		
		/*
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
		*/
		
		if(car.get().getPublisherType().getName().equals("AGENT")) {
			try {
				////////////////////////////////////////////////////////////////////////////////////////////////
				//SoapAgentByIdResponse response = new SoapAgentByIdResponse();
				
				Optional<AgentDbModel> agent = agentRepo.findById(car.get().getPublisherId());
				
				if(agent.isPresent()) {
					response.setPublisherName(agent.get().getName());
				}
				//response.getCar().setPublisherName(agent.get().getName());
				/*
				response.setName(agent.get().getName());
				response.setAddress(agent.get().getAddress());
				response.setLocationId(BigInteger.valueOf(agent.get().getLocationId()));
				response.setTaxId(agent.get().getTaxId());
				response.setId(BigInteger.valueOf(agent.get().getId()));
				
				response.setSuccess(true);
				return response;
				SoapAgentByIdResponse agentResponse = agentServiceClient.getAgent(car.get().getPublisherId());
				if(agentResponse.isSuccess()) {
					response.getCar().setPublisherName(agentResponse.getName());	
				}
				*/
				///////////////////////////////////////////////////////////////////////////////////////////////
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Fetch rating
		try {
			/*
			SoapCarRatingResponse carRatingResponse = scheduleServiceClient.getCarRating(request.getId());
			if (carRatingResponse.isSuccess()) {
				response.setRating(carRatingResponse.getRating());
			}
			*/			
			//InternalCarRatingResponse response = new InternalCarRatingResponse();
			int sum = 0;
			int count = 0;
			List<ReservationDbModel> dbResponses = reservationRepo.findByCarId(request.getId());
			for (ReservationDbModel objectIn : dbResponses) {
				if (objectIn.getRating() != null) {
					sum += objectIn.getRating();
					count++;
				}
			}
			response.setRating(Math.round((float) sum / (float) count));
			//response.setRating(Math.round((float) sum / (float) count));
			//response.setSuccess(true);
			//return response;
		} catch (Exception e) {
			System.out.println(e);
		}

		// Fetch images
		for (CarImageDbModel image : car.get().getImages()) {
			response.getImages().add(image.getId());
		}

		//response.setSuccess(true);
		//return response;
		/*
		SoapCarResponse internalResponse = carServiceClient.send(translator.translate(request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<HttpCarResponse>(translator.translate(internalResponse), HttpStatus.OK);
		*/
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable int id) {
		SoapGetImageRequest internalRequest = new SoapGetImageRequest();
		internalRequest.setId(id);

		SoapGetImageResponse internalResponse = carServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(internalResponse.getImage(), headers,
				HttpStatus.OK);
		return responseEntity;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/{id}/image")
	public ResponseEntity<Integer> postImage(@RequestHeader("token") String token, @PathVariable int id,
			@RequestParam("image") MultipartFile file) {
		SoapPostImageRequest internalRequest = new SoapPostImageRequest();

		internalRequest.setToken(token);
		internalRequest.setCarId(id);

		try {
			internalRequest.setImage(file.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		SoapPostImageResponse internalResponse = carServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(internalResponse.getImageId(),
				HttpStatus.OK);
		return responseEntity;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "car/image/{id}")
	public ResponseEntity<?> deleteImage(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteImageRequest internalRequest = new SoapDeleteImageRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/classes")
	public ResponseEntity<?> addCarClass(@RequestBody HttpCreateNamedObject request,
			@RequestHeader("token") String token) {
		SoapAddCarClassRequest internalRequest = new SoapAddCarClassRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/classes/{id}")
	public ResponseEntity<?> deleteCarClass(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteCarClassRequest internalRequest = new SoapDeleteCarClassRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/manufacturers")
	public ResponseEntity<?> addManufacturer(@RequestBody HttpCreateNamedObject request,
			@RequestHeader("token") String token) {
		SoapAddManufacturerRequest internalRequest = new SoapAddManufacturerRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/manufacturers/{id}")
	public ResponseEntity<?> deleteManufacturer(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteManufacturerRequest internalRequest = new SoapDeleteManufacturerRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/fuelTypes")
	public ResponseEntity<?> addFuelType(@RequestBody HttpCreateNamedObject request,
			@RequestHeader("token") String token) {
		SoapAddFuelTypeRequest internalRequest = new SoapAddFuelTypeRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/fuelTypes/{id}")
	public ResponseEntity<?> deleteFuelType(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteFuelTypeRequest internalRequest = new SoapDeleteFuelTypeRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/transmissionTypes")
	public ResponseEntity<?> addTransmissionType(@RequestBody HttpCreateNamedObject request,
			@RequestHeader("token") String token) {
		SoapAddTransmissionTypeRequest internalRequest = new SoapAddTransmissionTypeRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/transmissionTypes/{id}")
	public ResponseEntity<?> deleteTransmissionType(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteTransmissionTypeRequest internalRequest = new SoapDeleteTransmissionTypeRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/models")
	public ResponseEntity<?> addCarModel(@RequestBody HttpAddCarModel request, @RequestHeader("token") String token) {
		SoapAddCarModelRequest internalRequest = new SoapAddCarModelRequest();
		internalRequest.setName(request.getName());
		internalRequest.setManufacturerId(request.getManufacturerId());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/models/{id}")
	public ResponseEntity<?> deleteCarModel(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteCarModelRequest internalRequest = new SoapDeleteCarModelRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/search")
	public ResponseEntity<List<HttpCarResponse>> search(@RequestBody HttpSearchCarsRequest request) {
		
		SoapSearchCarsResponse internalResponse = carServiceClient.send(translator.translate(request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<HttpCarResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}


	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/add")
	public ResponseEntity<Integer> addCar(@RequestHeader("token") String token, @RequestBody HttpAddCarRequest request) {

		SoapAddCarResponse internalResponse = carServiceClient.send(translator.translate(request, token));

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(internalResponse.getId(), HttpStatus.OK);
		return responseEntity;
	}
}
