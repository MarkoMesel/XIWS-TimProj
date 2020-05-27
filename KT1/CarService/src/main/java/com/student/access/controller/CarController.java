package com.student.access.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarResponse;
import com.student.internal.contract.InternalAutherisedResponse;
import com.student.internal.contract.InternalCarResponse;
import com.student.internal.contract.InternalImageResponse;
import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;

@Controller
public class CarController {

	private CarProvider carProvider;
	private Translator translator;

	@Autowired
	public CarController(CarProvider carProvider, Translator translator) {
		super();
		this.carProvider = carProvider;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/search")
	public ResponseEntity<List<HttpCarResponse>> search(/* @RequestBody HttSearchRequest searchRequest */) {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getAllCars()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/getCar/{id}")
	public ResponseEntity<HttpCarResponse> getCar(@PathVariable int id) {
		InternalCarResponse internalResponse = carProvider.getCar(id);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<HttpCarResponse>(translator.httpTranslate(internalResponse), HttpStatus.OK);
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

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(translator.httpTranslate(internalResponse), headers, HttpStatus.OK);
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
	}
}
