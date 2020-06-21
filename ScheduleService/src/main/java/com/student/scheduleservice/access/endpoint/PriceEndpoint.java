package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.PriceProvider;
import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Endpoint
public class PriceEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private PriceProvider priceProvider;

	@Autowired
	public PriceEndpoint(PriceProvider priceProvider) {
		super();
		this.priceProvider = priceProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddPriceRequest")
	@ResponsePayload
	public SoapResponse addPrice(@RequestPayload SoapAddPriceRequest request) {
		return priceProvider.addPrice(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeletePriceRequest")
	@ResponsePayload
	public SoapResponse deletePrice(@RequestPayload SoapDeletePriceRequest  request) {
		return priceProvider.deletePrice(request);
	}
}
