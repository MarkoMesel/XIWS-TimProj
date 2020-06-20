package com.student.soap.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component("UserServiceClient")
public class UserServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
    public UserServiceClient(@Qualifier("userServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   
	
    @SuppressWarnings("unchecked")
	public <TRequest,TResponse> TResponse send(TRequest request){
        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("userservice");
        ServiceInstance scheduleInstance = scheduleInstances.get(0);
        String theScheme = scheduleInstance.getScheme();
        String theHost = scheduleInstance.getHost();
        int thePort = scheduleInstance.getPort();
        return (TResponse) webServiceTemplate.marshalSendAndReceive(theScheme + "://" + theHost + ":" + thePort + "/ws",request);
    }
}
