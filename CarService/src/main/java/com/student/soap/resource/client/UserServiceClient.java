package com.student.soap.resource.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.student.soap.userservice.contract.SoapGetResponse;
import com.student.soap.userservice.contract.SoapInternalGetUserRequest;

@Component("UserServiceClient")
public class UserServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
    public UserServiceClient(@Qualifier("userServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}

    public SoapGetResponse getUser(int id){
    	SoapInternalGetUserRequest request = new SoapInternalGetUserRequest();
    	request.setId(id);
    	List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("userservice");
        ServiceInstance sc = scheduleInstances.get(0);
        return (SoapGetResponse) webServiceTemplate.marshalSendAndReceive(sc.getScheme() + "://" 
				+ sc.getHost() + ":" 
				+ sc.getPort() + "/ws",
				request);
    }
}
