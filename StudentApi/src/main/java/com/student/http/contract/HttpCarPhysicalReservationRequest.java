package com.student.http.contract;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpCarPhysicalReservationRequest {

	private int carId;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;
	private int publisherId;
	private int publisherTypeId;
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}
	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
	}
	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}
	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public int getPublisherTypeId() {
		return publisherTypeId;
	}
	public void setPublisherTypeId(int publisherTypeId) {
		this.publisherTypeId = publisherTypeId;
	}
	
	
	
}
