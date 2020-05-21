package com.student.agentservice.access.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.agentservice.soap.contract.SoapAgentByIdRequest;
import com.student.agentservice.soap.contract.SoapAgentByIdResponse;
import com.student.agentservice.soap.contract.SoapAgentsRequest;
import com.student.agentservice.soap.contract.SoapAgentsResponse;

@Endpoint
public class AgentEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/agentservice/soap/contract";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAgentByIdRequest")
	@ResponsePayload
	public SoapAgentByIdResponse getAgents(@RequestPayload SoapAgentByIdRequest request) {
		return new SoapAgentByIdResponse();
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAgentsRequest")
	@ResponsePayload
	public SoapAgentsResponse getAgent(@RequestPayload SoapAgentsRequest request) {
		return new SoapAgentsResponse();
	}
}
