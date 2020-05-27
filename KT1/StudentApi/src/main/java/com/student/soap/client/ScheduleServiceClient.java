package com.student.soap.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component("ScheduleServiceClient")
public class ScheduleServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
    public ScheduleServiceClient(@Qualifier("scheduleServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   
	
    public <TRequest,TResponse> TResponse send(TRequest request){
        return (TResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8084/ws",request);
    }
}