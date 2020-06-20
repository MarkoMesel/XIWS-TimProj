package com.student.soap.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component("ScheduleServiceClient")
public class ScheduleServiceClient {
    
	private WebServiceTemplate webServiceTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
    public ScheduleServiceClient(@Qualifier("scheduleServiceMarshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
	}   
	
    @SuppressWarnings("unchecked")
	public <TRequest,TResponse> TResponse send(TRequest request){
    	/*
    	PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
        Applications applications = registry.getApplications();

        applications.getRegisteredApplications().forEach((registeredApplication) -> {
            registeredApplication.getInstances().forEach((instance) -> {
                System.out.println(instance.getAppName() + " (" + instance.getInstanceId() + ") : " + request);
            });
        });
        */
    	List<String> services = this.discoveryClient.getServices();
        //List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
        for(String s : services) {
        	System.out.println("I FOUND THIS: "+ s);
        }
        	/*
            this.discoveryClient.getInstances(serviceName).forEach(instance ->{
                instances.add(instance);
            });
            */
        	//System.out.println("I FOUND THIS: "+ serviceName);

        List<ServiceInstance> scheduleInstances = discoveryClient.getInstances("scheduleservice");
        ServiceInstance scheduleInstance = scheduleInstances.get(0);
        String theScheme = scheduleInstance.getScheme();
        System.out.println("THIS IS THE SCHEME: " + theScheme);
        String theHost = scheduleInstance.getHost();
        System.out.println("THIS IS THE HOST: " + theHost);
        int thePort = scheduleInstance.getPort();
        System.out.println("THIS IS THE PORT: " + thePort);
        return (TResponse) webServiceTemplate.marshalSendAndReceive(theScheme + "://" + theHost + ":" + thePort + "/ws",request);
        //return (TResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:" + thePortINeed + "/ws",request);
    }
}