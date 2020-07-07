package com.xiws.agentm.http.contract;

import javax.xml.datatype.XMLGregorianCalendar;

public class HttpSearchCarsRequest {
	protected int locationId;
	protected XMLGregorianCalendar startDate;
	protected XMLGregorianCalendar endDate;
	protected Integer publisherTypeId;
	protected Integer modelId;
	protected Integer manufacturerId;
	protected Integer fuelTypeId;
	protected Integer transmissionTypeId;
	protected Integer carClassId;
	protected Integer minMileage;
	protected Integer maxMileage;
	protected Integer minChildSeats;
	protected Integer minPrice;
	protected Integer maxPrice;
	protected Integer plannedMileage;
	protected Boolean collisionWarranty;
	
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}
	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
	}
	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}
	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
	}
	public Integer getPublisherTypeId() {
		return publisherTypeId;
	}
	public void setPublisherTypeId(Integer publisherTypeId) {
		this.publisherTypeId = publisherTypeId;
	}
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
	public Integer getMinMileage() {
		return minMileage;
	}
	public void setMinMileage(Integer minMileage) {
		this.minMileage = minMileage;
	}
	public Integer getMaxMileage() {
		return maxMileage;
	}
	public void setMaxMileage(Integer maxMileage) {
		this.maxMileage = maxMileage;
	}
	public Integer getMinChildSeats() {
		return minChildSeats;
	}
	public void setMinChildSeats(Integer minChildSeats) {
		this.minChildSeats = minChildSeats;
	}
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Integer getPlannedMileage() {
		return plannedMileage;
	}
	public void setPlannedMileage(Integer plannedMileage) {
		this.plannedMileage = plannedMileage;
	}
	public Boolean getCollisionWarranty() {
		return collisionWarranty;
	}
	public void setCollisionWarranty(Boolean collisionWarranty) {
		this.collisionWarranty = collisionWarranty;
	}
}
