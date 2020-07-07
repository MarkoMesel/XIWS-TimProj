package com.xiws.agentm.http.contract;

public class HttpAddCarRequest {
	private int modelId;
	private int locationId;
	private int fuelTypeId;
	private int transmissionTypeId;
	private int carClassId;
	private int mileage;
	private int childSeats;
	
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getFuelTypeId() {
		return fuelTypeId;
	}
	public void setFuelTypeId(int fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}
	public int getTransmissionTypeId() {
		return transmissionTypeId;
	}
	public void setTransmissionTypeId(int transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}
	public int getCarClassId() {
		return carClassId;
	}
	public void setCarClassId(int carClassId) {
		this.carClassId = carClassId;
	}
	public int getMileage() {
		return mileage;
	}
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public int getChildSeats() {
		return childSeats;
	}
	public void setChildSeats(int childSeats) {
		this.childSeats = childSeats;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
}
