package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpUnavailabilityRequest;
import com.student.http.contract.HttpUnavailabilityResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapAddUnavailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarUnavailabilitiesRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapUnavailabilityResponse;

@Controller
public class UnavailabilityController {
	private ScheduleServiceClient scheduleServiceClient;
	private Translator translator;

	@Autowired
	public UnavailabilityController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/car/unavailable")
	public ResponseEntity<?> addCarUnavailability(@RequestHeader("token") String token,
			@RequestBody HttpUnavailabilityRequest request) {
		SoapAddUnavailabilityRequest internalRequest = new SoapAddUnavailabilityRequest();

		internalRequest.setToken(token);
		internalRequest.setCarId(request.getCarId());
		internalRequest.setStartDate(request.getStartDate());
		internalRequest.setEndDate(request.getEndDate());

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/car/unavailabilities")
	public ResponseEntity<List<HttpUnavailabilityResponse>> getCarUnavailability(@RequestBody HttpUnavailabilityRequest request) {
		SoapCarUnavailabilitiesRequest internalRequest = new SoapCarUnavailabilitiesRequest();

		internalRequest.setCarId(request.getCarId());
		internalRequest.setStartDate(request.getStartDate());
		internalRequest.setEndDate(request.getEndDate());

		SoapUnavailabilityResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<HttpUnavailabilityResponse>>(translator.translate(internalResponse) , HttpStatus.OK);
	}
}
