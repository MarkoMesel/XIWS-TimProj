package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ChatProvider;
import com.student.soap.contract.scheduleservice.SoapAddMessageRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Endpoint
public class ChatEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private ChatProvider chatProvider;

	@Autowired
	public ChatEndpoint(ChatProvider chatProvider) {
		super();
		this.chatProvider = chatProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddMessageRequest")
	@ResponsePayload
	public SoapResponse addToCart(@RequestPayload SoapAddMessageRequest request) {
		return chatProvider.addMessage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapMessagesRequest")
	@ResponsePayload
	public SoapMessagesResponse getCart(@RequestPayload SoapMessagesRequest request) {
		return chatProvider.getMessages(request);
	}
}
