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

import com.student.http.contract.HttpAddReservationReportRequest;
import com.student.http.contract.HttpReservationResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapAddReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapPendingReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Controller
public class ReservationReportController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public ReservationReportController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/reservations/report/pending")
	public ResponseEntity<List<HttpReservationResponse>> getMessages(@RequestHeader("token") String token) {
		
		SoapPendingReservationReportRequest internalRequest = new SoapPendingReservationReportRequest();
		internalRequest.setToken(token);
		
		SoapReservationsResponse internalResponse = scheduleServiceClient.send(internalRequest);
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/reservations/report")
	public ResponseEntity<?> addRating(@RequestHeader("token") String token, @RequestBody HttpAddReservationReportRequest request) {
		SoapAddReservationReportRequest internalRequest = new SoapAddReservationReportRequest();
		internalRequest.setComment(request.getComment());
		internalRequest.setReservationId(request.getReservationId());
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
