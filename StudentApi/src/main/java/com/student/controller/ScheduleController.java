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
import com.student.http.contract.HttpAddPriceRequest;
import com.student.http.contract.HttpCarAvailabilityRequest;
import com.student.http.contract.HttpCarPhysicalReservationRequest;
import com.student.http.contract.HttpCommentResponse;
import com.student.http.contract.HttpRepliesAndCommentsResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.scheduleservice.contract.SoapAddCarPriceListRequest;
import com.student.soap.scheduleservice.contract.SoapAddPriceListRequest;
import com.student.soap.scheduleservice.contract.SoapAddPriceRequest;
import com.student.soap.scheduleservice.contract.SoapCarAvailabilityResponse;
import com.student.soap.scheduleservice.contract.SoapCarPhysicalRequest;
import com.student.soap.scheduleservice.contract.SoapCarPhysicalResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingsAndCommentsRequest;
import com.student.soap.scheduleservice.contract.SoapCarRatingsAndCommentsResponse;
import com.student.soap.scheduleservice.contract.SoapDeleteCarPriceListRequest;
import com.student.soap.scheduleservice.contract.SoapDeletePriceListRequest;
import com.student.soap.scheduleservice.contract.SoapDeletePriceRequest;
import com.student.soap.scheduleservice.contract.SoapResponse;

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
	@GetMapping(path = "schedule/car/{id}/rating")
	public ResponseEntity<Integer> getCarRating(@PathVariable("id") int id) {
		SoapCarRatingResponse internalResponse = scheduleServiceClient.send(translator.translateCarRating(id));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(internalResponse.getRating(), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/comments")
	public ResponseEntity<List<HttpCommentResponse>> getComments(@PathVariable("id") int id) {
		SoapCarRatingsAndCommentsRequest request = new SoapCarRatingsAndCommentsRequest();
		request.setId(id);
		SoapCarRatingsAndCommentsResponse internalResponse = scheduleServiceClient
				.send(translator.translateRatingsAndComments(id));
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/available")
	public ResponseEntity<Integer> getCarAvailability(@RequestBody HttpCarAvailabilityRequest request) {
		SoapCarAvailabilityResponse internalResponse = scheduleServiceClient.send(translator.translate(request));

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/car/physicalReservation")
	public ResponseEntity<Integer> getCarPhysicalReservation(@RequestHeader("token") String token,
			@RequestBody HttpCarPhysicalReservationRequest request) {
		SoapCarPhysicalRequest internalRequest = new SoapCarPhysicalRequest();

		internalRequest.setToken(token);
		internalRequest.setCarId(request.getCarId());
		internalRequest.setStartDate(request.getStartDate());
		internalRequest.setEndDate(request.getEndDate());
		internalRequest.setPublisherId(request.getPublisherId());
		internalRequest.setPublisherTypeId(request.getPublisherTypeId());

		SoapCarPhysicalResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/schedule/car/carPriceList")
	public ResponseEntity<?> addCarPriceList(@RequestBody HttpAddCarPriceListRequest request,
			@RequestHeader("token") String token) {
		SoapAddCarPriceListRequest internalRequest = new SoapAddCarPriceListRequest();

		internalRequest.setCarId(request.getCarId());
		internalRequest.setPriceListId(request.getCarPriceListId());
		internalRequest.setPublisherId(request.getPublisherId());
		internalRequest.setPublisherTypeId(request.getPublisherTypeId());
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
	@PostMapping(path = "/schedule/car/priceList")
	public ResponseEntity<?> addPriceList(@RequestBody HttpAddPriceListRequest request,
			@RequestHeader("token") String token) {

		SoapAddPriceListRequest internalRequest = new SoapAddPriceListRequest();
		internalRequest.setDiscountPercentage(request.getDiscountPercentage());
		internalRequest.setMileagePenalty(request.getMileagePenalty());
		internalRequest.setMileageThreshold(request.getMileageThreshold());
		internalRequest.setName(request.getName());
		internalRequest.setPublisherId(request.getPublisherId());
		internalRequest.setPublisherTypeId(request.getPublisherTypeId());
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
	@PostMapping(path = "schedule/car/price")
	public ResponseEntity<?> addDPrice(@RequestBody HttpAddPriceRequest request, @RequestHeader("token") String token) {
		SoapAddPriceRequest internalRequest = new SoapAddPriceRequest();

		internalRequest.setStartDate(request.getEndDate());
		internalRequest.setEndDate(request.getEndDate());
		internalRequest.setPrice(request.getPrice());
		internalRequest.setPriceListId(request.getPriceListId());
		internalRequest.setPublisherId(request.getPublisherId());
		internalRequest.setPublisherTypeId(request.getPublisherTypeId());
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
	@DeleteMapping(path = "schedule/car/carPriceList/{id}")
	public ResponseEntity<?> deleteCarPriceList(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteCarPriceListRequest internalRequest = new SoapDeleteCarPriceListRequest();
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
	@DeleteMapping(path = "schedule/car/pricelist/{id}")
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
	@DeleteMapping(path = "scheduleservice/car/price/{id}")
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
