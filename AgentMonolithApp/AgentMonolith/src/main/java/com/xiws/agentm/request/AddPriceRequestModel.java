package com.xiws.agentm.request;

import javax.xml.datatype.XMLGregorianCalendar;

public class AddPriceRequestModel {
	private int price;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;
	private int priceListId;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public int getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(int priceListId) {
		this.priceListId = priceListId;
	}
}
