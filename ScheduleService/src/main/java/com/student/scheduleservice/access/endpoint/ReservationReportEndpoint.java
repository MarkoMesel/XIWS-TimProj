package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.ReservationReportProvider;
import com.student.soap.contract.scheduleservice.SoapAddReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapPendingReservationReportRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Endpoint
public class ReservationReportEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private ReservationReportProvider reservationReportProvider;

	@Autowired
	public ReservationReportEndpoint(ReservationReportProvider reservationReportProvider) {
		super();
		this.reservationReportProvider = reservationReportProvider;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddReservationReportRequest")
	@ResponsePayload
	public SoapResponse addToCart(@RequestPayload SoapAddReservationReportRequest request) {
		return reservationReportProvider.addReport(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPendingReservationReportRequest")
	@ResponsePayload
	public SoapReservationsResponse getCart(@RequestPayload SoapPendingReservationReportRequest request) {
		return reservationReportProvider.getReservations(request);
	}
}
