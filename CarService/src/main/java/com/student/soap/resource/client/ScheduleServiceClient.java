package com.student.soap.resource.client;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.student.soap.scheduleservice.contract.SoapCarPriceRequest;
import com.student.soap.scheduleservice.contract.SoapCarPriceResponse;
import com.student.soap.scheduleservice.contract.SoapCarRatingRequest;
import com.student.soap.scheduleservice.contract.SoapCarRatingResponse;

@Component("ScheduleServiceClient")
public class ScheduleServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
    public ScheduleServiceClient(@Qualifier("scheduleServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   

    public SoapCarRatingResponse getCarRating(int id){
    	SoapCarRatingRequest request = new SoapCarRatingRequest();
    	request.setId(id);
        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("scheduleservice");
        ServiceInstance sc = scheduleInstances.get(0);
        return (SoapCarRatingResponse) webServiceTemplate.marshalSendAndReceive(sc.getScheme() + "://" 
				+ sc.getHost() + ":" 
				+ sc.getPort() + "/ws",
				request);
    }
    
    public SoapCarPriceResponse getCarPrice(int id, XMLGregorianCalendar startDate, XMLGregorianCalendar endDate){
    	SoapCarPriceRequest request = new SoapCarPriceRequest();
    	request.setId(id);
    	request.setStartDate(startDate);
    	request.setEndDate(endDate);
        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("scheduleservice");
        ServiceInstance sc = scheduleInstances.get(0);
        return (SoapCarPriceResponse) webServiceTemplate.marshalSendAndReceive(sc.getScheme() + "://" 
				+ sc.getHost() + ":" 
				+ sc.getPort() + "/ws",
				request);
    }
}
