package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.TransmissionTypeProvider;
import com.student.internal.translator.Translator;
import com.student.soap.carservice.contract.SoapAddTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapDeleteTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;
import com.student.soap.carservice.contract.SoapTransmissionTypesRequest;

@Endpoint
public class TransmissionTypeEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private TransmissionTypeProvider transmissionTypeProvider;
	private Translator translator;

	@Autowired
	public TransmissionTypeEndpoint(TransmissionTypeProvider transmissionTypeProvider, Translator translator) {
		this.transmissionTypeProvider = transmissionTypeProvider;
		this.translator = translator;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapTransmissionTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getTransmissionTypes(@RequestPayload SoapTransmissionTypesRequest request) {
		return translator.soapTranslate(transmissionTypeProvider.getTransmissionTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddTransmissionTypeRequest")
	@ResponsePayload
	public SoapResponse addTransmissionType(@RequestPayload SoapAddTransmissionTypeRequest request) {
		return transmissionTypeProvider.addTransmissionType(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteTransmissionTypeRequest")
	@ResponsePayload
	public SoapResponse deleteTransmissionType(@RequestPayload SoapDeleteTransmissionTypeRequest request) {
		return transmissionTypeProvider.deleteTransmissionType(request);
	}
}
