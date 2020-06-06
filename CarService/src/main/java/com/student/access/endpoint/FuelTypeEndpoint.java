package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAddFuelTypeRequest;
import com.student.soap.carservice.contract.SoapDeleteFuelTypeRequest;
import com.student.soap.carservice.contract.SoapFuelTypesRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class FuelTypeEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarProvider carProvider;
	private Translator translator;

	@Autowired
	public FuelTypeEndpoint(CarProvider carProvider, Translator translator) {
		this.carProvider = carProvider;
		this.translator = translator;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapFuelTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getFuelTypes(@RequestPayload SoapFuelTypesRequest request) {
		return translator.soapTranslate(carProvider.getFuelTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddFuelTypeRequest")
	@ResponsePayload
	public SoapResponse addFuelType(@RequestPayload SoapAddFuelTypeRequest request) {
		return carProvider.addFuelType(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteFuelTypeRequest")
	@ResponsePayload
	public SoapResponse deleteFuelType(@RequestPayload SoapDeleteFuelTypeRequest request) {
		return carProvider.deleteFuelType(request);
	}
}
