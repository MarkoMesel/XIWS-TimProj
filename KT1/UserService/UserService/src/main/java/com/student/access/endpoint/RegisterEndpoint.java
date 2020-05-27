package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.RegisterProvider;
import com.student.internal.translator.Translator;
import com.student.soap.contract.SoapRegisterRequest;
import com.student.soap.contract.SoapResponse;
import com.student.soap.contract.SoapVerifyRequest;

@Endpoint
public class RegisterEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private RegisterProvider registerProvider;
	private Translator translator;

	@Autowired
	public RegisterEndpoint(RegisterProvider registerProvider, Translator translator) {
		this.registerProvider = registerProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapRegisterRequest")
	@ResponsePayload
	public SoapResponse register(@RequestPayload SoapRegisterRequest request) {
		return translator.translate(registerProvider.register(translator.translate(request)));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapVerifyRequest")
	@ResponsePayload
	public SoapResponse verify(@RequestPayload SoapVerifyRequest request) {
		return translator.translate(registerProvider.verify(request.getToken()));
	}
}