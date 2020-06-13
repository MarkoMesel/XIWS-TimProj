package com.student.controller;

import java.io.IOException;
import java.util.List;

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

import com.student.http.contract.HttpAddCarModel;
import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpCreateNamedObject;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.http.contract.HttpSearchCarsRequest;
import com.student.internal.translator.Translator;
import com.student.soap.client.CarServiceClient;
import com.student.soap.contract.carservice.SoapAddCarClassRequest;
import com.student.soap.contract.carservice.SoapAddCarModelRequest;
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

@Controller
public class CarController {
	private Translator translator;
	private CarServiceClient carServiceClient;

	@Autowired
	public CarController(CarServiceClient carServiceClient, Translator translator) {
		this.carServiceClient = carServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/locations")
	public ResponseEntity<List<HttpNamedObjectResponse>> getAllLocations() {
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapLocationsRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/car/locations")
	public ResponseEntity<?> addLocation(@RequestBody HttpCreateNamedObject request,
			@RequestHeader("token") String token) {
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
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/car/locations/{id}")
	public ResponseEntity<?> deleteLocation(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteLocationRequest internalRequest = new SoapDeleteLocationRequest();

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
	@GetMapping(path = "/car/models")
	public ResponseEntity<List<HttpCarModelResponse>> getAllCarModels() {
		SoapCarModelsResponse internalResponse = carServiceClient.send(new SoapAllCarModelsRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/manufacturer/{manufacturerId}/models")
	public ResponseEntity<List<HttpCarModelResponse>> getCarModelsByManufacturerId(
			@PathVariable("manufacturerId") int manufacturerId) {
		SoapCarModelsByManufacturerRequest request = new SoapCarModelsByManufacturerRequest();
		request.setManufacturerId(manufacturerId);
		SoapCarModelsResponse internalResponse = carServiceClient.send(request);
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/manufacturers")
	public ResponseEntity<List<HttpNamedObjectResponse>> getCarManufacturers() {
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapCarManufacturersRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/fuelTypes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getFuelTypes() {
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapFuelTypesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/transmissionTypes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getTransmissionTypes() {
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapTransmissionTypesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/classes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getCarClasses() {
		SoapNamedObjectsResponse internalResponse = carServiceClient.send(new SoapCarClassesRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/getCar")
	public ResponseEntity<HttpCarResponse> getCar(@RequestBody HttpCarRequest request) {
		SoapCarResponse internalResponse = carServiceClient.send(translator.translate(request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<HttpCarResponse>(translator.translate(internalResponse), HttpStatus.OK);
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
	public ResponseEntity<?> addCar(@RequestHeader("token") String token, @RequestBody HttpAddCarRequest request) {

		SoapResponse internalResponse = carServiceClient.send(translator.translate(request, token));

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}
}
