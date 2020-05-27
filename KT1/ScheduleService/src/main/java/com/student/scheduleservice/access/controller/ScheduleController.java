package com.student.scheduleservice.access.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.student.scheduleservice.http.contract.HttpCommentResponse;
import com.student.scheduleservice.http.contract.HttpNamedObjectResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.internal.contract.InternalCommentsResponse;
import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;

@Controller
public class ScheduleController {
	
	private ScheduleProvider scheduleProvider;
	private Translator translator;
	
	@Autowired
	public ScheduleController(ScheduleProvider scheduleProvider, Translator translator) {
		super();
		this.scheduleProvider = scheduleProvider;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/schedule/locations")
	public ResponseEntity<List<HttpNamedObjectResponse>> getAllLocations() {
		return new ResponseEntity<>(translator.httpTranslate(scheduleProvider.getAllLocations()), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/comments")
	public ResponseEntity<List<HttpCommentResponse>> getCarComments(@PathVariable("id") int id) {
		InternalCommentsResponse internalResponse = scheduleProvider.getCarComments(id);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<HttpCommentResponse>>(translator.httpTranslate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/rating")
	public ResponseEntity<Integer> getCarRating(@PathVariable("id") int id) {
		InternalCarRatingResponse internalResponse = scheduleProvider.getCarRating(id);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(internalResponse.getRating(), HttpStatus.OK);
	}
}