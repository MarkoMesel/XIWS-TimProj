package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.LoginProvider;
import com.student.internal.translator.Translator;
import com.student.soap.contract.SoapLoginRequest;
import com.student.soap.contract.SoapLoginResponse;

@Endpoint
public class LoginEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private LoginProvider loginProvider;
	private Translator translator;

	@Autowired
	public LoginEndpoint(LoginProvider loginProvider, Translator translator) {
		this.loginProvider = loginProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapLoginRequest")
	@ResponsePayload
	public SoapLoginResponse login(@RequestPayload SoapLoginRequest request) {
		return translator.soapTranslate(loginProvider.login(translator.translate(request)));
	}
}