package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAddCarModelRequest;
import com.student.soap.carservice.contract.SoapAllCarModelsRequest;
import com.student.soap.carservice.contract.SoapCarModelsByManufacturerRequest;
import com.student.soap.carservice.contract.SoapCarModelsResponse;
import com.student.soap.carservice.contract.SoapDeleteCarModelRequest;
import com.student.soap.carservice.contract.SoapResponse;

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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarModelRequest")
	@ResponsePayload
	public SoapResponse addCarModel(@RequestPayload SoapAddCarModelRequest request) {
		return carProvider.addCarModel(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarModelRequest")
	@ResponsePayload
	public SoapResponse deleteCarModel(@RequestPayload SoapDeleteCarModelRequest request) {
		return carProvider.deleteCarModel(request);
	}
}
