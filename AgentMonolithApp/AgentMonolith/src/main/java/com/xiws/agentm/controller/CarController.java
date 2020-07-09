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

import com.xiws.agentm.request.NamedObjectRequestModel;
import com.xiws.agentm.request.SearchCarRequestModel;
import com.xiws.agentm.request.AddCarModelRequestModel;
import com.xiws.agentm.request.AddCarRequestModel;
import com.xiws.agentm.request.CarRequestModel;
import com.xiws.agentm.response.CarAndManufacturerResponseModel;
import com.xiws.agentm.response.CarPriceResponseModel;
import com.xiws.agentm.response.CarResponseModel;
import com.xiws.agentm.response.NamedObjectResponseModel;
import com.xiws.agentm.scheduleservice.data.dal.CarPriceListDbModel;
import com.xiws.agentm.scheduleservice.data.dal.PriceDbModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;
import com.xiws.agentm.scheduleservice.data.repo.CarPriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationRepo;
import com.xiws.agentm.Permission;
import com.xiws.agentm.agentservice.data.dal.AgentDbModel;
import com.xiws.agentm.agentservice.data.repo.AgentRepo;
import com.xiws.agentm.carservice.data.dal.CarClassDbModel;
import com.xiws.agentm.carservice.data.dal.CarDbModel;
import com.xiws.agentm.carservice.data.dal.CarImageDbModel;
import com.xiws.agentm.carservice.data.dal.CarManufacturerDbModel;
import com.xiws.agentm.carservice.data.dal.CarModelDbModel;
import com.xiws.agentm.carservice.data.dal.CarPublisherTypeDbModel;
import com.xiws.agentm.carservice.data.dal.FuelTypeDbModel;
import com.xiws.agentm.carservice.data.dal.LocationDbModel;
import com.xiws.agentm.carservice.data.dal.TransmissionTypeDbModel;
import com.xiws.agentm.carservice.data.repo.CarClassRepo;
import com.xiws.agentm.carservice.data.repo.CarImageRepo;
import com.xiws.agentm.carservice.data.repo.CarManufacturerRepo;
import com.xiws.agentm.carservice.data.repo.CarModelRepo;
import com.xiws.agentm.carservice.data.repo.CarPublisherTypeRepo;
import com.xiws.agentm.carservice.data.repo.CarRepo;
import com.xiws.agentm.carservice.data.repo.FuelTypeRepo;
import com.xiws.agentm.carservice.data.repo.LocationRepo;
import com.xiws.agentm.carservice.data.repo.TransmissionTypeRepo;
import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.JwtUtil;


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
	
	@Autowired
	CarImageRepo carImageRepo;
	
	@Autowired
	CarPublisherTypeRepo carPublisherTypeRepo;
	
	private JwtUtil jwtUtil;
/*	
	private Translator translator;
	private CarServiceClient carServiceClient;

	@Autowired
	public CarController(CarServiceClient carServiceClient, Translator translator) {
		this.carServiceClient = carServiceClient;
		this.translator = translator;
	}
*/
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
	public ResponseEntity<?> addLocation(@RequestBody NamedObjectRequestModel request,
			@RequestHeader("token") String token) {
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
	public ResponseEntity<?> deleteLocation(@RequestHeader("token") String token, @PathVariable int id) {
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
		return new ResponseEntity<CarResponseModel>(response, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable int id) {
		
		Optional<CarImageDbModel> image = carImageRepo.findById(id);
		
		if (!image.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(image.get().getImage(), headers,
				HttpStatus.OK);
		return responseEntity;
		
		/*
		SoapGetImageRequest internalRequest = new SoapGetImageRequest();
		internalRequest.setId(id);

		SoapGetImageResponse internalResponse = carServiceClient.send(internalRequest);
		
		SoapGetImageResponse response = new SoapGetImageResponse();
		*/

		/*
		response.setId(request.getId());
		response.setCarId(image.get().getCar().getId());
		response.setImage(image.get().getImage());

		response.setSuccess(true);
		return response;
		*/
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(internalResponse.getImage(), headers,
				HttpStatus.OK);
		return responseEntity;
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/{id}/image")
	public ResponseEntity<Integer> postImage(@RequestHeader("token") String token, @PathVariable int id,
			@RequestParam("image") MultipartFile file) {
		/*
		SoapPostImageRequest internalRequest = new SoapPostImageRequest();

		internalRequest.setToken(token);
		internalRequest.setCarId(id);

		try {
			internalRequest.setImage(file.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		SoapPostImageResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		
		//SoapPostImageResponse response = new SoapPostImageResponse();

		Optional<CarDbModel> car = carRepo.findById(id);
		if (!car.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		CarImageDbModel image = new CarImageDbModel();

		image.setCar(car.get());
		try {
			image.setImage(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		carImageRepo.save(image);
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(image.getId(),
				HttpStatus.OK);
		return responseEntity;
		
		/*
		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, car.get().getPublisherId(), car.get().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		/*
		response.setImageId(image.getId());
		response.setSuccess(true);
		return response;
		*/
		
		/*
		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(internalResponse.getImageId(),
				HttpStatus.OK);
		return responseEntity;
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "car/image/{id}")
	public ResponseEntity<?> deleteImage(@RequestHeader("token") String token, @PathVariable int id) {

		Optional<CarImageDbModel> image = carImageRepo.findById(id);
		if (!image.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		carImageRepo.delete(image.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapDeleteImageRequest internalRequest = new SoapDeleteImageRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();
		
		/*
		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, image.get().getCar().getPublisherId(),
				image.get().getCar().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		/*
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
	@PostMapping(path = "/car/classes")
	public ResponseEntity<?> addCarClass(@RequestBody NamedObjectRequestModel request
			,@RequestHeader("token") String token) {
		
		CarClassDbModel carClass = new CarClassDbModel();

		carClass.setName(request.getName());
		carClassRepo.save(carClass);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		/*
		SoapAddCarClassRequest internalRequest = new SoapAddCarClassRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();
		/*
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		//response.setSuccess(true);
		//return response;
		
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/classes/{id}")
	public ResponseEntity<?> deleteCarClass(@RequestHeader("token") String token, @PathVariable int id) {
		
		Optional<CarClassDbModel> carClass = carClassRepo.findById(id);
		if (!carClass.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			carClassRepo.delete(carClass.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapDeleteCarClassRequest internalRequest = new SoapDeleteCarClassRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);
		*/
		
		/*
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
	@PostMapping(path = "/car/manufacturers")
	public ResponseEntity<?> addManufacturer(@RequestBody NamedObjectRequestModel request
			,@RequestHeader("token") String token) {
		
		CarManufacturerDbModel manufacturer = new CarManufacturerDbModel();

		manufacturer.setName(request.getName());
		carManufacturerRepo.save(manufacturer);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);
		*/

		/*
		SoapAddManufacturerRequest internalRequest = new SoapAddManufacturerRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/manufacturers/{id}")
	public ResponseEntity<?> deleteManufacturer(@RequestHeader("token") String token, @PathVariable int id) {
		
		Optional<CarManufacturerDbModel> manufacturer = carManufacturerRepo.findById(id);
		if (!manufacturer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			carManufacturerRepo.delete(manufacturer.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		SoapDeleteManufacturerRequest internalRequest = new SoapDeleteManufacturerRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		/*
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
	@PostMapping(path = "/car/fuelTypes")
	public ResponseEntity<?> addFuelType(@RequestBody NamedObjectRequestModel request
			,@RequestHeader("token") String token) {
		
		FuelTypeDbModel fuelType = new FuelTypeDbModel();

		fuelType.setName(request.getName());
		fuelTypeRepo.save(fuelType);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapAddFuelTypeRequest internalRequest = new SoapAddFuelTypeRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/fuelTypes/{id}")
	public ResponseEntity<?> deleteFuelType(@RequestHeader("token") String token, @PathVariable int id) {
		
		Optional<FuelTypeDbModel> fuelType = fuelTypeRepo.findById(id);
		if (!fuelType.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			fuelTypeRepo.delete(fuelType.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapDeleteFuelTypeRequest internalRequest = new SoapDeleteFuelTypeRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		
		/*
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
	@PostMapping(path = "/car/transmissionTypes")
	public ResponseEntity<?> addTransmissionType(@RequestBody NamedObjectRequestModel request
			,@RequestHeader("token") String token) {
		
		TransmissionTypeDbModel transmissionType = new TransmissionTypeDbModel();

		transmissionType.setName(request.getName());
		transmissionTypeRepo.save(transmissionType);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapAddTransmissionTypeRequest internalRequest = new SoapAddTransmissionTypeRequest();
		internalRequest.setName(request.getName());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/transmissionTypes/{id}")
	public ResponseEntity<?> deleteTransmissionType(@RequestHeader("token") String token, @PathVariable int id) {
		
		Optional<TransmissionTypeDbModel> transmissionType = transmissionTypeRepo.findById(id);
		if (!transmissionType.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			transmissionTypeRepo.delete(transmissionType.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapDeleteTransmissionTypeRequest internalRequest = new SoapDeleteTransmissionTypeRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);
		*/
		
		/*
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
	@PostMapping(path = "/car/models")
	public ResponseEntity<?> addCarModel(@RequestBody AddCarModelRequestModel request, @RequestHeader("token") String token) {
		
		Optional<CarManufacturerDbModel> manufacturer = carManufacturerRepo.findById(request.getManufacturerId());
		if (!manufacturer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		CarModelDbModel carModel = new CarModelDbModel();

		carModel.setName(request.getName());
		carModel.setCarManufacturer(manufacturer.get());
		carModelRepo.save(carModel);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapAddCarModelRequest internalRequest = new SoapAddCarModelRequest();
		internalRequest.setName(request.getName());
		internalRequest.setManufacturerId(request.getManufacturerId());
		internalRequest.setToken(token);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/
		
		
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/models/{id}")
	public ResponseEntity<?> deleteCarModel(@RequestHeader("token") String token, @PathVariable int id) {
		
		Optional<CarModelDbModel> carModel = carModelRepo.findById(id);
		if (!carModel.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			carModelRepo.delete(carModel.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapDeleteCarModelRequest internalRequest = new SoapDeleteCarModelRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = carServiceClient.send(internalRequest);
		*/
		/*
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		*/

		/*
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
	@PostMapping(path = "car/search")
	public ResponseEntity<List<CarResponseModel>> search(@RequestBody SearchCarRequestModel request) {
		
		//SoapSearchCarsResponse internalResponse = carServiceClient.send(translator.translate(request));
		
		//SoapSearchCarsResponse response = new SoapSearchCarsResponse();
		
		List<CarResponseModel> internalResponse = new ArrayList<CarResponseModel>();
		
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		long requestMilis = request.getStartDate().toGregorianCalendar().getTimeInMillis();
        long timeDifference = requestMilis - gregorianCalendar.getTimeInMillis();
		if(timeDifference < 172800000 ) {
			//return response;
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<CarDbModel> cars = carRepo.findAll().stream()
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
			CarResponseModel objectOut = new CarResponseModel();

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
			/*
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
			*/
			
			if(objectIn.getPublisherType().getName().equals("AGENT")) {
				try {
					Optional<AgentDbModel> agent = agentRepo.findById(objectIn.getPublisherId());
					
					if(agent.isPresent()) {
						objectOut.setPublisherName(agent.get().getName());
					}
				} catch (Exception e) {
					System.out.println(e);
					continue;
				}
			}
			
			//Fetch images
			objectIn.getImages().forEach(image -> {
				objectOut.getImages().add(image.getId());
			});

			// Fetch rating
			try {
				int sum = 0;
				int count = 0;
				List<ReservationDbModel> dbResponses = reservationRepo.findByCarId(objectIn.getId());
				for (ReservationDbModel objectIn2 : dbResponses) {
					if (objectIn2.getRating() != null) {
						sum += objectIn2.getRating();
						count++;
					}
				}
				objectOut.setRating(Math.round((float) sum / (float) count));
			} catch (Exception e) {
				System.out.println(e);
				continue;
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
					CarPriceListDbModel carPricelist = carPriceListRepo.findByCarId(objectIn.getId()).stream()
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
					//if (carPriceResponse.isSuccess()) {
						
						//Does car have warranty if it's requested?
					if (request.getCollisionWarranty() != null && request.getCollisionWarranty() && carPriceResponse.getCollisionWarranty() == null) {
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
						objectOut.setEstimatedPenalty(penalty);
					} else {
						objectOut.setEstimatedPenalty(0);
					}
					//}
				} catch (Exception e) {
					System.out.println(e);
					continue;
				}
			}
			
			internalResponse.add(objectOut);
		}

		return new ResponseEntity<List<CarResponseModel>>(internalResponse, HttpStatus.OK);
		
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<HttpCarResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}


	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/add")
	public ResponseEntity<Integer> addCar(@RequestHeader("token") String tokenStr, @RequestBody AddCarRequestModel request) {

		//SoapAddCarResponse internalResponse = carServiceClient.send(translator.translate(request, token));
		
		/*
		SoapAddCarResponse response = new SoapAddCarResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == 1 ).findFirst().orElse(null);
		
		if(!authanticated(token, requiredPermission)){
			response.setAuthorized(false);
			return response;
		}
		*/
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(tokenStr);

		Permission requiredPermission = token.getPermissions().stream()
				.filter(permission -> permission.getPermissionId() == 1 ).findFirst().orElse(null);
		
		/*
		if(!authanticated(token, requiredPermission)){
			response.setAuthorized(false);
			return response;
		}
		*/
		
		int publisherId = requiredPermission.getResourceId();
		int publisherTypeId =  token.getRoleId();
		
		//response.setAuthorized(true);

		// If the publisher is a user, he can't have more than 3 cars
		if (token.getRoleId() == 1) {
			List<CarDbModel> cars = carRepo.findByPublisherIdAndPublisherTypeId(publisherId, publisherTypeId);
			if (cars.size() >= 3) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		CarDbModel car = new CarDbModel();
		
		Optional<LocationDbModel> location = locationRepo.findById(request.getLocationId());
		if (!location.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setLocation(location.get());
		
		Optional<CarClassDbModel> carClass = carClassRepo.findById(request.getCarClassId());
		if (!carClass.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setCarClass(carClass.get());
		
		Optional<CarModelDbModel> model =  carModelRepo.findById(request.getModelId());
		if(!model.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setCarModel(model.get());
		
		Optional<FuelTypeDbModel> fuelType = fuelTypeRepo.findById(request.getFuelTypeId());
		if(!fuelType.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setFuelType(fuelType.get());
		
		Optional<TransmissionTypeDbModel> transmissionType = transmissionTypeRepo.findById(request.getTransmissionTypeId());
		if(!transmissionType.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setTransmissionType(transmissionType.get());
		
		Optional<CarPublisherTypeDbModel> publisherType = carPublisherTypeRepo.findById(publisherTypeId);
		if(!publisherType.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		car.setPublisherType(publisherType.get());
		
		car.setChildSeats(request.getChildSeats());
		car.setMileage(request.getMileage());
		car.setPublisherId(publisherId);
		car.setActive(true);

		carRepo.save(car);
		
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(car.getId(), HttpStatus.OK);
		return responseEntity;
		
		/*
		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(internalResponse.getId(), HttpStatus.OK);
		return responseEntity;
		*/
	}
}
