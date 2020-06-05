package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;
import com.student.scheduleservice.soap.contract.SoapAddCarPriceListRequest;
import com.student.scheduleservice.soap.contract.SoapAddPriceListRequest;
import com.student.scheduleservice.soap.contract.SoapAddPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityRequest;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityResponse;
import com.student.scheduleservice.soap.contract.SoapCarPhysicalRequest;
import com.student.scheduleservice.soap.contract.SoapCarPhysicalResponse;
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarPriceResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsResponse;
import com.student.scheduleservice.soap.contract.SoapDeleteCarPriceListRequest;
import com.student.scheduleservice.soap.contract.SoapDeletePriceListRequest;
import com.student.scheduleservice.soap.contract.SoapDeletePriceRequest;
import com.student.scheduleservice.soap.contract.SoapResponse;

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
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarPhysicalRequest")
	@ResponsePayload
	public SoapCarPhysicalResponse getCarPhysical(@RequestPayload SoapCarPhysicalRequest request) {
		return scheduleProvider.getCarPhysical(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarPriceListRequest")
	@ResponsePayload
	public SoapResponse addCarPriceList(@RequestPayload SoapAddCarPriceListRequest request) {
		return scheduleProvider.addCarPriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarPriceListRequest")
	@ResponsePayload
	public SoapResponse deleteCarPriceList(@RequestPayload SoapDeleteCarPriceListRequest  request) {
		return scheduleProvider.deleteCarPriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddPriceListRequest")
	@ResponsePayload
	public SoapResponse addPriceList(@RequestPayload SoapAddPriceListRequest request) {
		return scheduleProvider.addPriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeletePriceListRequest")
	@ResponsePayload
	public SoapResponse deletePriceList(@RequestPayload SoapDeletePriceListRequest  request) {
		return scheduleProvider.deletePriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddPriceRequest")
	@ResponsePayload
	public SoapResponse addPrice(@RequestPayload SoapAddPriceRequest request) {
		return scheduleProvider.addPrice(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeletePriceRequest")
	@ResponsePayload
	public SoapResponse deletePrice(@RequestPayload SoapDeletePriceRequest  request) {
		return scheduleProvider.deletePrice(request);
	}
	
}
