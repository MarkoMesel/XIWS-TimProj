package com.student.scheduleservice.soap.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.student.soap.agentservice.contract.SoapAgentByIdRequest;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;

@Component("AgentServiceClient")
public class AgentServiceClient {
	
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	public AgentServiceClient(@Qualifier("agentServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
		webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}
	
	public <TRequest,TResponse> TResponse send(TRequest request){
        return (TResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8083/ws",request);
    }
	
	

}
