package com.student.internal.contract;

public class InternalCarModelResponse extends InternalResponse{
	private int modelId;
	private String modelName;
	private int manufacturerId;
	private String manufacturerName;
	
	public InternalCarModelResponse(int modelId, String modelName, int manufacturerId, String manufacturerName) {
		super();
		this.modelId = modelId;
		this.modelName = modelName;
		this.manufacturerId = manufacturerId;
		this.manufacturerName = manufacturerName;
	}
	
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public int getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
}
