package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.soap.carservice.contract.SoapAddLocationRequest;
import com.student.soap.carservice.contract.SoapDeleteLocationRequest;
import com.student.soap.carservice.contract.SoapLocationsRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class LocationEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarProvider carProvider;

	@Autowired
	public LocationEndpoint(CarProvider carProvider) {
		this.carProvider = carProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapLocationsRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getLocations(@RequestPayload SoapLocationsRequest request) {
		return carProvider.getAllLocations();
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddLocationRequest")
	@ResponsePayload
	public SoapResponse addLocation(@RequestPayload SoapAddLocationRequest request) {
		return carProvider.addLocation(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteLocationRequest")
	@ResponsePayload
	public SoapResponse deleteLocation(@RequestPayload SoapDeleteLocationRequest request) {
		return carProvider.deleteLocation(request);
	}
}
