package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.ManufacturerProvider;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAddManufacturerRequest;
import com.student.soap.carservice.contract.SoapCarManufacturersRequest;
import com.student.soap.carservice.contract.SoapDeleteManufacturerRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class ManufacturerEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private ManufacturerProvider manufacturerProvider;
	private Translator translator;

	@Autowired
	public ManufacturerEndpoint(ManufacturerProvider manufacturerProvider, Translator translator) {
		this.manufacturerProvider = manufacturerProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarManufacturersRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarManufacturers(@RequestPayload SoapCarManufacturersRequest request) {
		return translator.soapTranslate(manufacturerProvider.getCarManufacturers());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddManufacturerRequest")
	@ResponsePayload
	public SoapResponse addCarManufacturer(@RequestPayload SoapAddManufacturerRequest request) {
		return manufacturerProvider.addManufacturer(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteManufacturerRequest")
	@ResponsePayload
	public SoapResponse deleteCarManufacturer(@RequestPayload SoapDeleteManufacturerRequest request) {
		return manufacturerProvider.deleteManufacturer(request);
	}
}
