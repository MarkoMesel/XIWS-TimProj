package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.UserProvider;
import com.student.internal.translator.Translator;
import com.student.soap.contract.SoapEditRequest;
import com.student.soap.contract.SoapGetRequest;
import com.student.soap.contract.SoapGetResponse;
import com.student.soap.contract.SoapResponse;

@Endpoint
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private UserProvider userProvider;
	private Translator translator;

	@Autowired
	public UserEndpoint(UserProvider userProvider, Translator translator) {
		this.userProvider = userProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapEditRequest")
	@ResponsePayload
	public SoapResponse edit(@RequestPayload SoapEditRequest request) {
		return translator.translate(userProvider.edit(translator.translate(request)));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetRequest")
	@ResponsePayload
	public SoapGetResponse get(@RequestPayload SoapGetRequest request) {
		return translator.soapTranslate(userProvider.get(request.getToken()));
	}
}