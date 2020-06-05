package com.student.http.contract;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpAddPriceRequest {
	private int id;
	private int price;
	private XMLGregorianCalendar startDate;
	private XMLGregorianCalendar endDate;
	private int priceListId;
	private int publisherId;
	private int publisherTypeId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
