package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAllCarModelsRequest;
import com.student.soap.carservice.contract.SoapCarClassesRequest;
import com.student.soap.carservice.contract.SoapCarManufacturersRequest;
import com.student.soap.carservice.contract.SoapCarModelsByManufacturerRequest;
import com.student.soap.carservice.contract.SoapCarModelsResponse;
import com.student.soap.carservice.contract.SoapFuelTypesRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapTransmissionTypesRequest;
import com.student.soap.client.CarServiceClient;

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


	/*
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/getCar/{id}")
	public ResponseEntity<HttpCarResponse> getCar(@PathVariable int id) {
		SoapCarRequest request = new SoapCarRequest();
		request.setId(id);
		
		SoapCarResponse internalResponse = carServiceClient.send(request);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<HttpCarResponse>(translator.httpTranslate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/search")
	public ResponseEntity<List<HttpCarResponse>> search() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getAllCars()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/getImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable int id) {

		InternalImageResponse internalResponse = carProvider.getCarImage(id);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(translator.httpTranslate(internalResponse),
				headers, HttpStatus.OK);
		return responseEntity;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "car/add")
	public ResponseEntity<?> addCar(@RequestHeader("token") String token, @RequestBody HttpAddCarRequest request) {

		InternalAutherisedResponse internalResponse = carProvider.addCar(translator.translate(token, request));

		if (!internalResponse.isAutherised()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}*/
}
