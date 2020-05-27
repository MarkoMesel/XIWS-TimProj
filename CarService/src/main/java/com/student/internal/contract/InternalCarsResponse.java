package com.student.internal.contract;

import java.util.ArrayList;
import java.util.List;

public class InternalCarsResponse extends InternalResponse{
	private List<Car> cars;
	
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public void addCar(Car car) {
		cars.add(car);
	}
	
	public InternalCarsResponse() {
		cars = new ArrayList<>();
	}
	
	public static class Car {
		private List<Integer> carImages;
		private int id;
		private int modelId;
		private String modelName;
		private int manufacturerId;
		private String manufacturerName;
		private String fuelType;
		private int fuelTypeId;
		private String transmission;
		private int transmissionTypeId;
		private String carClass;
		private int carClassId;
		private int pricePerDay;
		private boolean collisionWaranty;
		private int mileage;
		private int mileageThreshold;
		private int mileagePenalty;
		private int childSeats;
		private int carRating;
		private int agentId;
		private String agentName;
		
		public Car() {
			carImages = new ArrayList<>();
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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

		public String getFuelType() {
			return fuelType;
		}

		public void setFuelType(String fuelType) {
			this.fuelType = fuelType;
		}

		public int getFuelTypeId() {
			return fuelTypeId;
		}

		public void setFuelTypeId(int fuelTypeId) {
			this.fuelTypeId = fuelTypeId;
		}

		public String getTransmission() {
			return transmission;
		}

		public void setTransmission(String transmission) {
			this.transmission = transmission;
		}

		public int getTransmissionTypeId() {
			return transmissionTypeId;
		}

		public void setTransmissionTypeId(int transmissionTypeId) {
			this.transmissionTypeId = transmissionTypeId;
		}

		public String getCarClass() {
			return carClass;
		}

		public void setCarClass(String carClass) {
			this.carClass = carClass;
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
		
		public List<Integer> getCarImages() {
			return carImages;
		}

		public void setCarImages(List<Integer> carImages) {
			this.carImages = carImages;
		}
		public int getCarRating() {
			return carRating;
		}
		public void setCarRating(int carRating) {
			this.carRating = carRating;
		}
		public int getAgentId() {
			return agentId;
		}
		public void setAgentId(int agentId) {
			this.agentId = agentId;
		}
		public String getAgentName() {
			return agentName;
		}
		public void setAgentName(String agentName) {
			this.agentName = agentName;
		}
	}
}
