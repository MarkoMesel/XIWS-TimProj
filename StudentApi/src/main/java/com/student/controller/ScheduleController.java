package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.student.http.contract.HttpNamedObjectResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.scheduleservice.contract.SoapLocationsRequest;
import com.student.soap.scheduleservice.contract.SoapNamedObjectsResponse;

@Controller
public class ScheduleController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public ScheduleController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/schedule/locations")
	public ResponseEntity<List<HttpNamedObjectResponse>> getAllLocations() {
		SoapNamedObjectsResponse internalResponse = scheduleServiceClient.send(new SoapLocationsRequest());
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/rating")
	public ResponseEntity<Integer> getCarRating(@PathVariable("id") int id) {
		SoapCarRatingResponse internalResponse = scheduleServiceClient.send(translator.translateCarRating(id));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(internalResponse.getRating(), HttpStatus.OK);
	}
}
