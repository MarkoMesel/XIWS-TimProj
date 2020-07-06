package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarClassProvider;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAddCarClassRequest;
import com.student.soap.carservice.contract.SoapCarClassesRequest;
import com.student.soap.carservice.contract.SoapDeleteCarClassRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class CarClassEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarClassProvider carClassProvider;
	private Translator translator;

	@Autowired
	public CarClassEndpoint(CarClassProvider carClassProvider, Translator translator) {
		this.carClassProvider = carClassProvider;
		this.translator = translator;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarClassesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarClasses(@RequestPayload SoapCarClassesRequest request) {
		return translator.soapTranslate(carClassProvider.getCarClasses());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarClassRequest")
	@ResponsePayload
	public SoapResponse deleteCarClass(@RequestPayload SoapDeleteCarClassRequest request) {
		return carClassProvider.deleteCarClass(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarClassRequest")
	@ResponsePayload
	public SoapResponse addCarClass(@RequestPayload SoapAddCarClassRequest request) {
		return carClassProvider.addCarClass(request);
	}
}
