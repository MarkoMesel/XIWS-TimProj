package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;
import com.student.soap.contract.SoapAllCarModelsRequest;
import com.student.soap.contract.SoapCarClassesRequest;
import com.student.soap.contract.SoapCarManufacturersRequest;
import com.student.soap.contract.SoapCarModelsByManufacturerRequest;
import com.student.soap.contract.SoapCarModelsResponse;
import com.student.soap.contract.SoapCarRequest;
import com.student.soap.contract.SoapCarResponse;
import com.student.soap.contract.SoapFuelTypesRequest;
import com.student.soap.contract.SoapNamedObjectsResponse;
import com.student.soap.contract.SoapTransmissionTypesRequest;

@Endpoint
public class CarModelEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarProvider carProvider;
	private Translator translator;

	@Autowired
	public CarModelEndpoint(CarProvider carProvider, Translator translator) {
		this.carProvider = carProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarManufacturersRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarManufacturers(@RequestPayload SoapCarManufacturersRequest request) {
		return translator.soapTranslate(carProvider.getCarManufacturers());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAllCarModelsRequest")
	@ResponsePayload
	public SoapCarModelsResponse getAllCarModels(@RequestPayload SoapAllCarModelsRequest request) {
		return translator.soapTranslate(carProvider.getAllCarModels());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarModelsByManufacturerRequest")
	@ResponsePayload
	public SoapCarModelsResponse getCarModelsByManufacturerId(@RequestPayload SoapCarModelsByManufacturerRequest request) {
		return translator.soapTranslate(carProvider.getCarModelsByManufacturerId(request.getManufacturerId()));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapFuelTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getFuelTypes(@RequestPayload SoapFuelTypesRequest request) {
		return translator.soapTranslate(carProvider.getFuelTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapTransmissionTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getTransmissionTypes(@RequestPayload SoapTransmissionTypesRequest request) {
		return translator.soapTranslate(carProvider.getTransmissionTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarClassesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarClasses(@RequestPayload SoapCarClassesRequest request) {
		return translator.soapTranslate(carProvider.getCarClasses());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRequest")
	@ResponsePayload
	public SoapCarResponse getCarClasses(@RequestPayload SoapCarRequest request) {
		return carProvider.getCar(request);
	}
}
