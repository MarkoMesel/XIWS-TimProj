package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpCartAddCarRequest;
import com.student.http.contract.HttpCartBundleRequest;
import com.student.http.contract.HttpBundleResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCartRemoveCarRequest;
import com.student.soap.contract.scheduleservice.SoapCartRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

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
	public ResponseEntity<?> addToCart(@RequestHeader("token") String token,
			@RequestBody HttpCartAddCarRequest request) {
		SoapResponse internalResponse = scheduleServiceClient.send(translator.translate(token, request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/cart")
	public ResponseEntity<List<HttpBundleResponse>> getCart(@RequestHeader("token") String token) {
		SoapCartRequest internalRequest = new SoapCartRequest();
		internalRequest.setToken(token);
		SoapBundlesResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpBundleResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "schedule/cart/{reservationId}")
	public ResponseEntity<?> removeCarFromCart(@RequestHeader("token") String token, @PathVariable int reservationId) {
		SoapCartRemoveCarRequest internalRequest = new SoapCartRemoveCarRequest();
		internalRequest.setToken(token);
		internalRequest.setReservationId(reservationId);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/cart/bundle")
	public ResponseEntity<?> bundle(@RequestHeader("token") String token,
			@RequestBody HttpCartBundleRequest request) {
		
		SoapResponse internalResponse = scheduleServiceClient.send(translator.translateBundle(token, request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/cart/unbundle")
	public ResponseEntity<?> unbundle(@RequestHeader("token") String token,
			@RequestBody HttpCartBundleRequest request) {
		
		SoapResponse internalResponse = scheduleServiceClient.send(translator.translateUnbundle(token, request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
