package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ScheduleProvider;
import com.student.scheduleservice.internal.translator.Translator;
import com.student.soap.contract.scheduleservice.SoapAddCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
import com.student.soap.contract.scheduleservice.SoapCarAvailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarAvailabilityResponse;
import com.student.soap.contract.scheduleservice.SoapCarPhysicalRequest;
import com.student.soap.contract.scheduleservice.SoapCarPhysicalResponse;
import com.student.soap.contract.scheduleservice.SoapCarPriceRequest;
import com.student.soap.contract.scheduleservice.SoapCarPriceResponse;
import com.student.soap.contract.scheduleservice.SoapCarRatingRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingResponse;
import com.student.soap.contract.scheduleservice.SoapDeleteCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarAvailabilityRequest")
	@ResponsePayload
	public SoapCarAvailabilityResponse getAvailabilityResponse(@RequestPayload SoapCarAvailabilityRequest request) {
		return scheduleProvider.getCarAvailability(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarPhysicalRequest")
	@ResponsePayload
	public SoapCarPhysicalResponse getCarPhysical(@RequestPayload SoapCarPhysicalRequest request) {
		return scheduleProvider.addUnavailability(request);
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
