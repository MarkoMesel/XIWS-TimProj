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

import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAllCarModelsRequest;
import com.student.soap.carservice.contract.SoapCarClassesRequest;
import com.student.soap.carservice.contract.SoapCarManufacturersRequest;
import com.student.soap.carservice.contract.SoapCarModelsByManufacturerRequest;
import com.student.soap.carservice.contract.SoapCarModelsResponse;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;
import com.student.soap.carservice.contract.SoapDeleteImageRequest;
import com.student.soap.carservice.contract.SoapFuelTypesRequest;
import com.student.soap.carservice.contract.SoapGetImageRequest;
import com.student.soap.carservice.contract.SoapGetImageResponse;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapPostImageRequest;
import com.student.soap.carservice.contract.SoapPostImageResponse;
import com.student.soap.carservice.contract.SoapResponse;
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

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/getCar")
	public ResponseEntity<HttpCarResponse> getCar(@RequestBody HttpCarRequest request) {

		SoapCarRequest internalRequest;

		try {
			internalRequest = translator.translate(request);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		SoapCarResponse internalResponse = carServiceClient.send(internalRequest);

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

	/*
	 * @CrossOrigin(origins = "*", allowedHeaders = "*")
	 * 
	 * @GetMapping(path = "car/search") public ResponseEntity<List<HttpCarResponse>>
	 * search() { return new
	 * ResponseEntity<>(translator.httpTranslate(carProvider.getAllCars()),
	 * HttpStatus.OK); }
	 * 
	 * @CrossOrigin(origins = "*", allowedHeaders = "*")
	 * 
	 * @GetMapping(path = "car/getImage/{id}", produces =
	 * MediaType.IMAGE_JPEG_VALUE) public ResponseEntity<byte[]>
	 * getImage(@PathVariable int id) {
	 * 
	 * InternalImageResponse internalResponse = carProvider.getCarImage(id);
	 * 
	 * if (!internalResponse.isSuccess()) { return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); }
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * 
	 * headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	 * 
	 * ResponseEntity<byte[]> responseEntity = new
	 * ResponseEntity<byte[]>(translator.httpTranslate(internalResponse), headers,
	 * HttpStatus.OK); return responseEntity; }
	 * 
	 * @CrossOrigin(origins = "*", allowedHeaders = "*")
	 * 
	 * @PostMapping(path = "car/add") public ResponseEntity<?>
	 * addCar(@RequestHeader("token") String token, @RequestBody HttpAddCarRequest
	 * request) {
	 * 
	 * InternalAutherisedResponse internalResponse =
	 * carProvider.addCar(translator.translate(token, request));
	 * 
	 * if (!internalResponse.isAutherised()) { return new
	 * ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
	 * 
	 * if (!internalResponse.isSuccess()) { return new
	 * ResponseEntity<>(HttpStatus.BAD_REQUEST); }
	 * 
	 * ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
	 * return responseEntity; }
	 */
}
