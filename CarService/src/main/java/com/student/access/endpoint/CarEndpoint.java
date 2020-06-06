package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.soap.carservice.contract.SoapAddCarRequest;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;
import com.student.soap.carservice.contract.SoapDeactivatePublisherRequest;
import com.student.soap.carservice.contract.SoapResponse;
import com.student.soap.carservice.contract.SoapSearchCarsRequest;
import com.student.soap.carservice.contract.SoapSearchCarsResponse;

@Endpoint
public class CarEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarProvider carProvider;

	@Autowired
	public CarEndpoint(CarProvider carProvider) {
		this.carProvider = carProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRequest")
	@ResponsePayload
	public SoapCarResponse getCar(@RequestPayload SoapCarRequest request) {
		return carProvider.getCar(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeactivatePublisherRequest")
	@ResponsePayload
	public SoapResponse deactivatePublisher(@RequestPayload SoapDeactivatePublisherRequest request) {
		return carProvider.deactivatePublisher(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarRequest")
	@ResponsePayload
	public SoapResponse addCar(@RequestPayload SoapAddCarRequest request) {
		return carProvider.addCar(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapSearchCarsRequest")
	@ResponsePayload
	public SoapSearchCarsResponse search(@RequestPayload SoapSearchCarsRequest request) {
		return carProvider.seachCars(request);
	}
}
