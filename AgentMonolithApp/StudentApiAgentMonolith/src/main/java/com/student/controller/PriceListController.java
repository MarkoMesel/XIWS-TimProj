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

import com.student.http.contract.HttpAddCarPriceListRequest;
import com.student.http.contract.HttpAddPriceListRequest;
import com.student.http.contract.HttpPriceListResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
import com.student.soap.contract.scheduleservice.SoapPriceListResponse;
import com.student.soap.contract.scheduleservice.SoapPriceListsResponse;
import com.student.soap.contract.scheduleservice.SoapPublisherPriceListsRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapSetCarPriceListRequest;

@Controller
public class PriceListController {
	private ScheduleServiceClient scheduleServiceClient;
	private Translator translator;

	@Autowired
	public PriceListController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/priceLists")
	public ResponseEntity<List<HttpPriceListResponse>> getPublisherPriceLists(@RequestHeader("token") String token) {
		SoapPublisherPriceListsRequest request = new SoapPublisherPriceListsRequest();
		request.setToken(token);
		SoapPriceListsResponse internalResponse = scheduleServiceClient.send(request);
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<HttpPriceListResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/schedule/priceList")
	public ResponseEntity<?> addPriceList(@RequestBody HttpAddPriceListRequest request,
			@RequestHeader("token") String token) {

		SoapAddPriceListRequest internalRequest = new SoapAddPriceListRequest();
		internalRequest.setDiscountPercentage(request.getDiscountPercentage());
		internalRequest.setMileagePenalty(request.getMileagePenalty());
		internalRequest.setMileageThreshold(request.getMileageThreshold());
		internalRequest.setName(request.getName());
		internalRequest.setWarrantyPrice(request.getWarrantyPrice());
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
	@DeleteMapping(path = "schedule/priceList/{id}")
	public ResponseEntity<?> deletePriceList(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeletePriceListRequest internalRequest = new SoapDeletePriceListRequest();

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
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/schedule/car/priceList")
	public ResponseEntity<?> setCarPriceList(@RequestBody HttpAddCarPriceListRequest request,
			@RequestHeader("token") String token) {
		SoapSetCarPriceListRequest internalRequest = new SoapSetCarPriceListRequest();

		internalRequest.setCarId(request.getCarId());
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
	@GetMapping(path = "schedule/car/{carId}/priceList")
	public ResponseEntity<HttpPriceListResponse> getCarPriceList(@PathVariable int carId) {
		SoapCarPriceListRequest request = new SoapCarPriceListRequest();
		request.setCarId(carId);
		SoapPriceListResponse internalResponse = scheduleServiceClient.send(request);
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HttpPriceListResponse>(translator.translate(internalResponse), HttpStatus.OK);
	}
}
