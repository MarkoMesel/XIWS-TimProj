package com.siteproj0.demo.rentedcar;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.siteproj0.demo.car.Car;

public class RentedCar {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty
	String startDate;
	
	@NotEmpty
	String endDate;
	
	Car car;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
}
