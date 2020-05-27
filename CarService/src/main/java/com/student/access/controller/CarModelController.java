package com.student.access.controller;

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
import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;

@Controller
public class CarModelController {

	private CarProvider carProvider;
	private Translator translator;

	@Autowired
	public CarModelController(CarProvider carProvider, Translator translator) {
		super();
		this.carProvider = carProvider;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/models")
	public ResponseEntity<List<HttpCarModelResponse>> getAllCarModels() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getAllCarModels()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/car/manufacturer/{manufacturerId}/models")
	public ResponseEntity<List<HttpCarModelResponse>> getCarModelsByManufacturerId(
			@PathVariable("manufacturerId") int manufacturerId) {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getCarModelsByManufacturerId(manufacturerId)),
				HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/manufacturers")
	public ResponseEntity<List<HttpNamedObjectResponse>> getCarManufacturers() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getCarManufacturers()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/fuelTypes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getFuelTypes() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getFuelTypes()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/transmissionTypes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getTransmissionTypes() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getTransmissionTypes()), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/classes")
	public ResponseEntity<List<HttpNamedObjectResponse>> getCarClasses() {
		return new ResponseEntity<>(translator.httpTranslate(carProvider.getCarClasses()), HttpStatus.OK);
	}
}
