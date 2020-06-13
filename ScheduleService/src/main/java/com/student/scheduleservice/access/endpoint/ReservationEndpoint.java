package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ReservationProvider;
import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
import com.student.soap.contract.scheduleservice.SoapPayReservationRequest;
import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsRequest;
import com.student.soap.contract.scheduleservice.SoapReserveRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Endpoint
public class ReservationEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private ReservationProvider reservationProvider;

	@Autowired
	public ReservationEndpoint(ReservationProvider reservationProvider) {
		super();
		this.reservationProvider = reservationProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapReserveRequest")
	@ResponsePayload
	public SoapResponse reserve(@RequestPayload SoapReserveRequest request) {
		return reservationProvider.reserve(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SoapApproveReservationRequest")
	@ResponsePayload
	public SoapResponse approve(@RequestPayload SoapApproveReservationRequest request) {
		return reservationProvider.approve(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapRejectReservationRequest")
	@ResponsePayload
	public SoapResponse reject(@RequestPayload SoapRejectReservationRequest request) {
		return reservationProvider.reject(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCancelReservationRequest")
	@ResponsePayload
	public SoapResponse cancel(@RequestPayload SoapCancelReservationRequest request) {
		return reservationProvider.cancel(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPayReservationRequest")
	@ResponsePayload
	public SoapResponse pay(@RequestPayload SoapPayReservationRequest request) {
		return reservationProvider.pay(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapReservationsRequest")
	@ResponsePayload
	public SoapBundlesResponse pay(@RequestPayload SoapReservationsRequest request) {
		return reservationProvider.getBundles(request);
	}
}
