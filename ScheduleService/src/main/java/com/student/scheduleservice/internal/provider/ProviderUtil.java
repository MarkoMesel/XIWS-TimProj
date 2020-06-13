package com.student.scheduleservice.internal.provider;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.soap.client.AgentServiceClient;
import com.student.scheduleservice.soap.client.UserServiceClient;
import com.student.soap.contract.agentservice.SoapAgentByIdResponse;
import com.student.soap.contract.userservice.SoapGetResponse;

@Component("ProviderUtil")
public class ProviderUtil {
	
	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;
	
	@Autowired
	public ProviderUtil(UserServiceClient userServiceClient, AgentServiceClient agentServiceClient) {
		super();
		this.userServiceClient = userServiceClient;
		this.agentServiceClient = agentServiceClient;
	}

	public String fetchPublisherName(String publisherTypeName, Integer publisherId) {
		//Fetch publisher name
		//If user
		if(publisherTypeName.equals("USER")) {
			try {
				SoapGetResponse userResponse = userServiceClient.getUser(publisherId);
				if(userResponse.isSuccess()) {
					return userResponse.getFirstName()+" "+userResponse.getLastName();	
				}
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		
		//If agent
		if(publisherTypeName.equals("AGENT")) {
			try {
				SoapAgentByIdResponse agentResponse = agentServiceClient.getAgent(publisherId);
				if(agentResponse.isSuccess()) {
					return agentResponse.getName();
				}
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		return null;
	}
	
	public XMLGregorianCalendar getXmlGregorianCalendar(Long timestamp) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public XMLGregorianCalendar getXmlGregorianCalendar(Date date) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
