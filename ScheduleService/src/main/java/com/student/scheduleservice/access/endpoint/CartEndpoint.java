package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.CartProvider;
import com.student.scheduleservice.soap.contract.SoapCartAddCarRequest;
import com.student.scheduleservice.soap.contract.SoapCartRequest;
import com.student.scheduleservice.soap.contract.SoapCartResponse;
import com.student.scheduleservice.soap.contract.SoapResponse;

@Endpoint
public class CartEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private CartProvider cartProvider;

	@Autowired
	public CartEndpoint(CartProvider cartProvider) {
		super();
		this.cartProvider = cartProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCartAddCarRequest")
	@ResponsePayload
	public SoapResponse addToCart(@RequestPayload SoapCartAddCarRequest request) {
		return cartProvider.addCarToCart(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCartRequest")
	@ResponsePayload
	public SoapCartResponse getCart(@RequestPayload SoapCartRequest request) {
		return cartProvider.getCart(request);
	}
}
