package com.student.scheduleservice.soap.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.student.soap.contract.agentservice.SoapAgentByIdRequest;
import com.student.soap.contract.agentservice.SoapAgentByIdResponse;

@Component("AgentServiceClient")
public class AgentServiceClient {
	
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	public AgentServiceClient(@Qualifier("agentServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
		webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}
	
	@SuppressWarnings("unchecked")
	public <TRequest,TResponse> TResponse send(TRequest request){
        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("agentservice");
        ServiceInstance sc = scheduleInstances.get(0);
        return (TResponse) webServiceTemplate.marshalSendAndReceive(sc.getScheme() + "://" 
        															+ sc.getHost() + ":" 
        															+ sc.getPort() + "/ws",
        															request);
    }
	
    public SoapAgentByIdResponse getAgent(int id){
    	SoapAgentByIdRequest request = new SoapAgentByIdRequest();
    	request.setAgentId(id);
        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("agentservice");
        ServiceInstance sc = scheduleInstances.get(0);
        return (SoapAgentByIdResponse) webServiceTemplate.marshalSendAndReceive(sc.getScheme() + "://" 
				+ sc.getHost() + ":" 
				+ sc.getPort() + "/ws",
				request);
    }
}
