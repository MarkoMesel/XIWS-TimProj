package com.student.http.contract;

public class HttpAddCarPriceListRequest {

	private int carId;
	private int priceListId;

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(int priceListId) {
		this.priceListId = priceListId;
	}
}
