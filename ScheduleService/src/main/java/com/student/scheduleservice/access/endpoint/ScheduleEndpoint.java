package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;
import com.student.soap.contract.scheduleservice.SoapCarPriceRequest;
import com.student.soap.contract.scheduleservice.SoapCarPriceResponse;
import com.student.soap.contract.scheduleservice.SoapCarRatingRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingResponse;

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
	public SoapCarPriceResponse getCarPrice(@RequestPayload SoapCarPriceRequest request) {
		return translator.soapTranslate(scheduleProvider.getCarPrice(translator.translate(request)));
	}
}
