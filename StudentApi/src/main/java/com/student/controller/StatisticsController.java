package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpCarResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.CarServiceClient;
import com.student.soap.contract.carservice.SoapCarsResponse;
import com.student.soap.contract.carservice.SoapGetTopCarsByCommentCountRequest;
import com.student.soap.contract.carservice.SoapGetTopCarsByMileageRequest;
import com.student.soap.contract.carservice.SoapGetTopCarsByRatingRequest;

@Controller
public class StatisticsController {
	private Translator translator;
	private CarServiceClient carServiceClient;

	@Autowired
	public StatisticsController(CarServiceClient carServiceClient, Translator translator) {
		this.carServiceClient = carServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/statistics/mileage")
	public ResponseEntity<List<HttpCarResponse>> statisticsByMileage(@RequestHeader("token") String token) {
		SoapGetTopCarsByMileageRequest internalRequest = new SoapGetTopCarsByMileageRequest();
		internalRequest.setToken(token);
		SoapCarsResponse  internalResponse = carServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<List<HttpCarResponse>> responseEntity = new ResponseEntity<List<HttpCarResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		return responseEntity;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/statistics/comments")
	public ResponseEntity<List<HttpCarResponse>> statisticsByCommentCount(@RequestHeader("token") String token) {
		SoapGetTopCarsByCommentCountRequest internalRequest = new SoapGetTopCarsByCommentCountRequest();
		internalRequest.setToken(token);
		SoapCarsResponse  internalResponse = carServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<List<HttpCarResponse>> responseEntity = new ResponseEntity<List<HttpCarResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		return responseEntity;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "car/statistics/rating")
	public ResponseEntity<List<HttpCarResponse>> statisticsByRating(@RequestHeader("token") String token) {
		SoapGetTopCarsByRatingRequest internalRequest = new SoapGetTopCarsByRatingRequest();
		internalRequest.setToken(token);
		SoapCarsResponse  internalResponse = carServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<List<HttpCarResponse>> responseEntity = new ResponseEntity<List<HttpCarResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		return responseEntity;
	}
}
