package com.siteproj0.demo.car;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Car")
public class CarDbModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	private String vehicleMark;
	
	@NotEmpty
	private String vehicleModel;
	
	@NotEmpty
	private String fuelType;
	
	@NotEmpty
	private String transmission;
	
	@NotEmpty
	private String vehicleClass;
	
	@NotEmpty
	private int mileage;
	
	@NotEmpty
	private int expectedMileage;
	
	@NotEmpty
	private int numberOfSeats;
	
	@NotEmpty
	private float bottomPrice;
	
	@NotEmpty
	private float topPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehicleMark() {
		return vehicleMark;
	}

	public void setVehicleMark(String vehicleMark) {
		this.vehicleMark = vehicleMark;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getExpectedMileage() {
		return expectedMileage;
	}

	public void setExpectedMileage(int expectedMileage) {
		this.expectedMileage = expectedMileage;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public float getBottomPrice() {
		return bottomPrice;
	}

	public void setBottomPrice(float bottomPrice) {
		this.bottomPrice = bottomPrice;
	}

	public float getTopPrice() {
		return topPrice;
	}

	public void setTopPrice(float topPrice) {
		this.topPrice = topPrice;
	}
	
}
