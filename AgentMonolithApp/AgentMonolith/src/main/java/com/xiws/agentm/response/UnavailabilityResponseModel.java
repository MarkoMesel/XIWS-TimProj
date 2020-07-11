package com.xiws.agentm.response;

import javax.xml.datatype.XMLGregorianCalendar;

public class UnavailabilityResponseModel {
	private int id;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
