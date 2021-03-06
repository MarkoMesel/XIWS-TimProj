package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarImageProvider;
import com.student.soap.carservice.contract.SoapDeleteImageRequest;
import com.student.soap.carservice.contract.SoapGetImageRequest;
import com.student.soap.carservice.contract.SoapGetImageResponse;
import com.student.soap.carservice.contract.SoapPostImageRequest;
import com.student.soap.carservice.contract.SoapPostImageResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Endpoint
public class CarImageEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarImageProvider carImageProvider;

	@Autowired
	public CarImageEndpoint(CarImageProvider carImageProvider) {
		this.carImageProvider = carImageProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetImageRequest")
	@ResponsePayload
	public SoapGetImageResponse getCarImage(@RequestPayload SoapGetImageRequest request) {
		return carImageProvider.getCarImage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPostImageRequest")
	@ResponsePayload
	public SoapPostImageResponse postCarImage(@RequestPayload SoapPostImageRequest request) {
		return carImageProvider.postCarImage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteImageRequest")
	@ResponsePayload
	public SoapResponse deleteImage(@RequestPayload SoapDeleteImageRequest request) {
		return carImageProvider.deleteCarImage(request);
	}
}
