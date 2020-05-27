package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarPriceResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsResponse;
import com.student.scheduleservice.soap.contract.SoapLocationsRequest;
import com.student.scheduleservice.soap.contract.SoapNamedObjectsResponse;

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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapLocationsRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getLocations(@RequestPayload SoapLocationsRequest request) {
		return translator.soapTranslate(scheduleProvider.getAllLocations());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRatingsAndCommentsRequest")
	@ResponsePayload
	public SoapCarRatingsAndCommentsResponse getCommnentsAndRatings(@RequestPayload SoapCarRatingsAndCommentsRequest request) {
		return scheduleProvider.getCarRatingsAndComments(request.getId());
	}
}
