package com.xiws.agentm.request;

import javax.xml.datatype.XMLGregorianCalendar;

public class UnavailabilityRequestModel {
	private int carId;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;

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
}
