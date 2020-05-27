package com.student.soap.resource.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.student.soap.resourse.contract.SoapCarRatingRequest;
import com.student.soap.resourse.contract.SoapCarRatingResponse;

@Component("ScheduleServiceClient")
public class ScheduleServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
    public ScheduleServiceClient(Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   

    public SoapCarRatingResponse getCarRating(int id){
    	SoapCarRatingRequest request = new SoapCarRatingRequest();
    	request.setId(id);
        return (SoapCarRatingResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8084/ws",request);
    }
}
