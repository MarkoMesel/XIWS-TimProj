package com.student.internal.contract;

import java.util.ArrayList;
import java.util.List;

public class InternalCarModelsResponse extends InternalResponse{
	private List<CarModel> carModels;
	
	public InternalCarModelsResponse() {
		super();
		carModels = new ArrayList<>();
	}

	public List<CarModel> getCarModels() {
		return carModels;
	}

	public void setCarModels(List<CarModel> carModels) {
		this.carModels = carModels;
	}
	
	public void addCarModel(CarModel carModel) {
		this.carModels.add(carModel);
	}
	
	public static class CarModel {
		private int modelId;
		private String modelName;
		private int manufacturerId;
		private String manufacturerName;
		
		public CarModel(int modelId, String modelName, int manufacturerId, String manufacturerName) {
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
}
