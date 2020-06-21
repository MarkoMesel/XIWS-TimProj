package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.PriceListProvider;
import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
import com.student.soap.contract.scheduleservice.SoapPriceListResponse;
import com.student.soap.contract.scheduleservice.SoapPriceListsResponse;
import com.student.soap.contract.scheduleservice.SoapPublisherPriceListsRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapSetCarPriceListRequest;

@Endpoint
public class PriceListEndoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private PriceListProvider priceListProvider;

	@Autowired
	public PriceListEndoint(PriceListProvider priceListProvider) {
		super();
		this.priceListProvider = priceListProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddPriceListRequest")
	@ResponsePayload
	public SoapResponse addPriceList(@RequestPayload SoapAddPriceListRequest request) {
		return priceListProvider.addPriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeletePriceListRequest")
	@ResponsePayload
	public SoapResponse deletePriceList(@RequestPayload SoapDeletePriceListRequest  request) {
		return priceListProvider.deletePriceList(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPublisherPriceListsRequest")
	@ResponsePayload
	public SoapPriceListsResponse getPublisherPriceLists(@RequestPayload SoapPublisherPriceListsRequest  request) {
		return priceListProvider.getPublisherPriceLists(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarPriceListRequest")
	@ResponsePayload
	public SoapPriceListResponse getCarPricelist(@RequestPayload SoapCarPriceListRequest  request) {
		return priceListProvider.getCarPricelist(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapSetCarPriceListRequest")
	@ResponsePayload
	public SoapResponse setCarPriceList(@RequestPayload SoapSetCarPriceListRequest  request) {
		return priceListProvider.setCarPriceList(request);
	}
}
