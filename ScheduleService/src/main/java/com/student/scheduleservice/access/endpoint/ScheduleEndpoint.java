package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityRequest;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityResponse;
<<<<<<< HEAD
import com.student.scheduleservice.soap.contract.SoapCarPhysicalRequest;
import com.student.scheduleservice.soap.contract.SoapCarPhysicalResponse;
=======
>>>>>>> 90ae7a7f0050ecc99b4b2d909c7a43c5b16043ab
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarPriceResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsResponse;

@Endpoint
public class ScheduleEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private ScheduleProvider scheduleProvider;
	private Translator translator;

	@Autowired
	public ScheduleEndpoint(ScheduleProvider scheduleProvider, Translator translator) {
		super();
		this.scheduleProvider = scheduleProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRatingRequest")
	@ResponsePayload
	public SoapCarRatingResponse getCarRating(@RequestPayload SoapCarRatingRequest request) {
		return translator.soapTranslate(scheduleProvider.getCarRating(request.getId()));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarPriceRequest")
	@ResponsePayload
	public SoapCarPriceResponse getCarRating(@RequestPayload SoapCarPriceRequest request) {
		return translator.soapTranslate(scheduleProvider.getCarPrice(translator.translate(request)));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarAvailabilityRequest")
	@ResponsePayload
	public SoapCarAvailabilityResponse getAvailabilityResponse(@RequestPayload SoapCarAvailabilityRequest request) {
		return scheduleProvider.getCarAvailability(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRatingsAndCommentsRequest")
	@ResponsePayload
	public SoapCarRatingsAndCommentsResponse getCommentsAndRatings(@RequestPayload SoapCarRatingsAndCommentsRequest request) {
		return scheduleProvider.getCarRatingsAndComments(request.getId());
	}
	
<<<<<<< HEAD
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarPhysicalRequest")
	@ResponsePayload
	public SoapCarPhysicalResponse getCarPhysical(@RequestPayload SoapCarPhysicalRequest request) {
		return scheduleProvider.getCarPhysical(request);
	}
	
=======
>>>>>>> 90ae7a7f0050ecc99b4b2d909c7a43c5b16043ab
	
}
