package com.student.agentservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.agentservice.internal.provider.AgentProvider;
import com.student.agentservice.soap.contract.SoapAgentByIdRequest;
import com.student.agentservice.soap.contract.SoapAgentByIdResponse;
import com.student.agentservice.soap.contract.SoapAgentsRequest;
import com.student.agentservice.soap.contract.SoapAgentsResponse;

import javassist.Translator;

@Endpoint
public class AgentEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/agentservice/soap/contract";
	
	private AgentProvider agentProvider;
	
	@Autowired
	public AgentEndpoint(AgentProvider agentProvide) {
		this.agentProvider = agentProvider;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAgentByIdRequest")
	@ResponsePayload
	public SoapAgentByIdResponse getAgents(@RequestPayload SoapAgentByIdRequest request) {
		return agentProvider.getAgent(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAgentsRequest")
	@ResponsePayload
	public SoapAgentsResponse getAgent(@RequestPayload SoapAgentsRequest request) {
		return new SoapAgentsResponse();
	}
}
