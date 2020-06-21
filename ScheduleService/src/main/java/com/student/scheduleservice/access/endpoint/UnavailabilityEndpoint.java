package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.UnavailabilityProvider;
import com.student.soap.contract.scheduleservice.SoapAddUnavailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarUnavailabilitiesRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapUnavailabilityResponse;

@Endpoint
public class UnavailabilityEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private UnavailabilityProvider unavailabilityProvider;

	@Autowired
	public UnavailabilityEndpoint(UnavailabilityProvider unavailabilityProvider) {
		super();
		this.unavailabilityProvider = unavailabilityProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddUnavailabilityRequest")
	@ResponsePayload
	public SoapResponse addToCart(@RequestPayload SoapAddUnavailabilityRequest request) {
		return unavailabilityProvider.addUnavailability(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarUnavailabilitiesRequest")
	@ResponsePayload
	public SoapUnavailabilityResponse getCarUnavailabilitiesRequest(@RequestPayload SoapCarUnavailabilitiesRequest request) {
		return unavailabilityProvider.getCarUnavailability(request);
	}
}
