package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.StatisticsProvider;
import com.student.soap.carservice.contract.SoapCarsResponse;
import com.student.soap.carservice.contract.SoapGetTopCarsByCommentCountRequest;
import com.student.soap.carservice.contract.SoapGetTopCarsByMileageRequest;
import com.student.soap.carservice.contract.SoapGetTopCarsByRatingRequest;

@Endpoint
public class StatisticsEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private StatisticsProvider statisticsProvider;

	@Autowired
	public StatisticsEndpoint(StatisticsProvider statisticsProvider) {
		this.statisticsProvider = statisticsProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetTopCarsByMileageRequest")
	@ResponsePayload
	public SoapCarsResponse getByMileage(@RequestPayload SoapGetTopCarsByMileageRequest request) {
		return statisticsProvider.getByMileage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetTopCarsByCommentCountRequest")
	@ResponsePayload
	public SoapCarsResponse getByComments(@RequestPayload SoapGetTopCarsByCommentCountRequest request) {
		return statisticsProvider.getByComments(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetTopCarsByRatingRequest")
	@ResponsePayload
	public SoapCarsResponse getByRating(@RequestPayload SoapGetTopCarsByRatingRequest request) {
		return statisticsProvider.getByRating(request);
	}
}
