package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpCartAddCarRequest;
import com.student.http.contract.HttpCartResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.scheduleservice.contract.SoapCartRequest;
import com.student.soap.scheduleservice.contract.SoapCartResponse;
import com.student.soap.scheduleservice.contract.SoapResponse;

@Controller
public class CartController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public CartController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/cart")
	public ResponseEntity<?> addToCart(@RequestHeader("token") String token, @RequestBody HttpCartAddCarRequest request) {
		SoapResponse internalResponse = scheduleServiceClient.send(translator.translate(token, request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/cart")
	public ResponseEntity<List<HttpCartResponse>> getCart(@RequestHeader("token") String token) {
		SoapCartRequest internalRequest = new SoapCartRequest();
		internalRequest.setToken(token);
		SoapCartResponse internalResponse = scheduleServiceClient.send(internalRequest);
		
		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpCartResponse>>(translator.translate(internalResponse),HttpStatus.OK);
	}
}
