package com.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpAddPriceRequest;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Controller
public class PriceController {
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public PriceController(ScheduleServiceClient scheduleServiceClient) {
		this.scheduleServiceClient = scheduleServiceClient;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/price")
	public ResponseEntity<?> addPrice(@RequestBody HttpAddPriceRequest request, @RequestHeader("token") String token) {
		SoapAddPriceRequest internalRequest = new SoapAddPriceRequest();

		internalRequest.setStartDate(request.getStartDate());
		internalRequest.setEndDate(request.getEndDate());
		internalRequest.setPrice(request.getPrice());
		internalRequest.setPriceListId(request.getPriceListId());
		internalRequest.setToken(token);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "schedule/price/{id}")
	public ResponseEntity<?> deletePrice(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeletePriceRequest internalRequest = new SoapDeletePriceRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
