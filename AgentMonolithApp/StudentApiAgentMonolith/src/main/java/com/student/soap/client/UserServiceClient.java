package com.student.soap.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component("UserServiceClient")
public class UserServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
    public UserServiceClient(@Qualifier("userServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   
	
    @SuppressWarnings("unchecked")
	public <TRequest,TResponse> TResponse send(TRequest request){
        return (TResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8081/ws",request);
    }
}
