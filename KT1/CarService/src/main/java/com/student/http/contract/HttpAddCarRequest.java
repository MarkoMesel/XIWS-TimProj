package com.student.http.contract;

public class HttpAddCarRequest {
	private int modelId;
	private int fuelTypeId;
	private int transmissionTypeId;
	private int carClassId;
	private int pricePerDay;
	private boolean collisionWaranty;
	private int mileage;
	private int mileageThreshold;
	private int mileagePenalty;
	private int childSeats;
	private int agentId;
	
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

	public int getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isCollisionWaranty() {
		return collisionWaranty;
	}

	public void setCollisionWaranty(boolean collisionWaranty) {
		this.collisionWaranty = collisionWaranty;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getMileageThreshold() {
		return mileageThreshold;
	}

	public void setMileageThreshold(int mileageThreshold) {
		this.mileageThreshold = mileageThreshold;
	}

	public int getMileagePenalty() {
		return mileagePenalty;
	}

	public void setMileagePenalty(int mileagePenalty) {
		this.mileagePenalty = mileagePenalty;
	}

	public int getChildSeats() {
		return childSeats;
	}

	public void setChildSeats(int childSeats) {
		this.childSeats = childSeats;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
}
