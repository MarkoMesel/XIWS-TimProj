package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.LocationProvider;
import com.student.soap.carservice.contract.SoapAddLocationRequest;
import com.student.soap.carservice.contract.SoapDeleteLocationRequest;
import com.student.soap.carservice.contract.SoapLocationsRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class LocationEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private LocationProvider locationProvider;

	@Autowired
	public LocationEndpoint(LocationProvider locationProvider) {
		this.locationProvider = locationProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapLocationsRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getLocations(@RequestPayload SoapLocationsRequest request) {
		return locationProvider.getAllLocations();
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddLocationRequest")
	@ResponsePayload
	public SoapResponse addLocation(@RequestPayload SoapAddLocationRequest request) {
		return locationProvider.addLocation(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteLocationRequest")
	@ResponsePayload
	public SoapResponse deleteLocation(@RequestPayload SoapDeleteLocationRequest request) {
		return locationProvider.deleteLocation(request);
	}
}
