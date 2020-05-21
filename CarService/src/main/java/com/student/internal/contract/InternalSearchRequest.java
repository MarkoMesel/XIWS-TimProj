package com.student.internal.contract;

public class InternalSearchRequest extends InternalResponse {
	private Integer modelId;
	private Integer manufacturerId;
	private Integer fuelTypeId;
	private Integer transmissionTypeId;
	private Integer carClassId;
	//TODO: Add missing fields
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public Integer getFuelTypeId() {
		return fuelTypeId;
	}
	public void setFuelTypeId(Integer fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}
	public Integer getTransmissionTypeId() {
		return transmissionTypeId;
	}
	public void setTransmissionTypeId(Integer transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}
	public Integer getCarClassId() {
		return carClassId;
	}
	public void setCarClassId(Integer carClassId) {
		this.carClassId = carClassId;
	}
}
