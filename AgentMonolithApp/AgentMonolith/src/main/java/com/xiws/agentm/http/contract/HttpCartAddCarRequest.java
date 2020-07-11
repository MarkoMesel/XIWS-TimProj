package com.xiws.agentm.http.contract;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpCartAddCarRequest {
	private int carId;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;
	private boolean collisionWarranty;
	

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
	public boolean isCollisionWarranty() {
		return collisionWarranty;
	}
	public void setCollisionWarranty(boolean collisionWarranty) {
		this.collisionWarranty = collisionWarranty;
	}
}
