package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarModelProvider;
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

	private CarModelProvider carModelProvider;
	private Translator translator;

	@Autowired
	public CarModelEndpoint(CarModelProvider carModelProvider, Translator translator) {
		this.carModelProvider = carModelProvider;
		this.translator = translator;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAllCarModelsRequest")
	@ResponsePayload
	public SoapCarModelsResponse getAllCarModels(@RequestPayload SoapAllCarModelsRequest request) {
		return translator.soapTranslate(carModelProvider.getAllCarModels());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarModelsByManufacturerRequest")
	@ResponsePayload
	public SoapCarModelsResponse getCarModelsByManufacturerId(@RequestPayload SoapCarModelsByManufacturerRequest request) {
		return translator.soapTranslate(carModelProvider.getCarModelsByManufacturerId(request.getManufacturerId()));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarModelRequest")
	@ResponsePayload
	public SoapResponse addCarModel(@RequestPayload SoapAddCarModelRequest request) {
		return carModelProvider.addCarModel(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarModelRequest")
	@ResponsePayload
	public SoapResponse deleteCarModel(@RequestPayload SoapDeleteCarModelRequest request) {
		return carModelProvider.deleteCarModel(request);
	}
}
