package com.student.http.contract;

public class HttpAddCarPriceListRequest {

	private int carId;
	private int carPriceListId;
	private int publisherId;
	private int publisherTypeId;
	
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
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getCarPriceListId() {
		return carPriceListId;
	}
	public void setCarPriceListId(int carPriceListId) {
		this.carPriceListId = carPriceListId;
	}
}
