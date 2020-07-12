package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpBundleResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
import com.student.soap.contract.scheduleservice.SoapPublisherReservationsRequest;
import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
import com.student.soap.contract.scheduleservice.SoapReserveRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapUserReservationsRequest;

@Controller
public class ReservationController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public ReservationController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/user")
	public ResponseEntity<List<HttpBundleResponse>> getUserReservations(@RequestHeader("token") String token) {
		SoapUserReservationsRequest internalRequest = new SoapUserReservationsRequest();
		internalRequest.setToken(token);
		SoapBundlesResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpBundleResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/publisher")
	public ResponseEntity<List<HttpBundleResponse>> getPublisherReservations(@RequestHeader("token") String token) {
		SoapPublisherReservationsRequest internalRequest = new SoapPublisherReservationsRequest();
		internalRequest.setToken(token);
		SoapBundlesResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpBundleResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/reserve")
	public ResponseEntity<List<HttpBundleResponse>> reserve(@RequestHeader("token") String token) {
		SoapReserveRequest internalRequest = new SoapReserveRequest();
		internalRequest.setToken(token);
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/approve")
	public ResponseEntity<?> approve(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		SoapApproveReservationRequest internalRequest = new SoapApproveReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/reject")
	public ResponseEntity<?> reject(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		SoapRejectReservationRequest internalRequest = new SoapRejectReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/bundle/{bundleId}/cancel")
	public ResponseEntity<?> cancel(@RequestHeader("token") String token, @PathVariable int bundleId ) {
		SoapCancelReservationRequest internalRequest = new SoapCancelReservationRequest();
		internalRequest.setBundleId(bundleId);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
