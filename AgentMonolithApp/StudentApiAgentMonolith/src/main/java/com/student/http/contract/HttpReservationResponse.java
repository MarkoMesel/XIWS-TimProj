package com.student.http.contract;

import java.util.ArrayList;
import java.util.List;

public class HttpReservationResponse {
	private Integer carId;
	private Integer reservationId;
	private Integer locationId;
	private String locationName;
	private Integer modelId;
	private String modelName;
	private Integer manufacturerId;
	private String manufacturerName;
	private Integer fuelTypeId;
	private String fuelTypeName;
	private Integer transmissionTypeId;
	private String transmissionTypeName;
	private Integer carClassId;
	private String carClassName;
	private Integer mileage;
	private Integer mileageThreshold;
	private Integer mileagePenalty;
	private Boolean warrantyIncluded;
	private Integer totalPrice;
	private Integer extraCharges;
	private Integer childSeats;
	private Integer rating;
	private Integer publisherId;
	private String publisherName;
	private Integer publisherTypeId;
	private String publisherTypeName;
	private List<Integer> images;

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public Integer getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(Integer fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	public String getFuelTypeName() {
		return fuelTypeName;
	}

	public void setFuelTypeName(String fuelTypeName) {
		this.fuelTypeName = fuelTypeName;
	}

	public Integer getTransmissionTypeId() {
		return transmissionTypeId;
	}

	public void setTransmissionTypeId(Integer transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}

	public String getTransmissionTypeName() {
		return transmissionTypeName;
	}

	public void setTransmissionTypeName(String transmissionTypeName) {
		this.transmissionTypeName = transmissionTypeName;
	}

	public Integer getCarClassId() {
		return carClassId;
	}

	public void setCarClassId(Integer carClassId) {
		this.carClassId = carClassId;
	}

	public String getCarClassName() {
		return carClassName;
	}

	public void setCarClassName(String carClassName) {
		this.carClassName = carClassName;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getMileageThreshold() {
		return mileageThreshold;
	}

	public void setMileageThreshold(Integer mileageThreshold) {
		this.mileageThreshold = mileageThreshold;
	}

	public Integer getMileagePenalty() {
		return mileagePenalty;
	}

	public void setMileagePenalty(Integer mileagePenalty) {
		this.mileagePenalty = mileagePenalty;
	}

	public Boolean getWarrantyIncluded() {
		return warrantyIncluded;
	}

	public void setWarrantyIncluded(Boolean warrantyIncluded) {
		this.warrantyIncluded = warrantyIncluded;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getChildSeats() {
		return childSeats;
	}

	public void setChildSeats(Integer childSeats) {
		this.childSeats = childSeats;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Integer getPublisherTypeId() {
		return publisherTypeId;
	}

	public void setPublisherTypeId(Integer publisherTypeId) {
		this.publisherTypeId = publisherTypeId;
	}

	public String getPublisherTypeName() {
		return publisherTypeName;
	}

	public void setPublisherTypeName(String publisherTypeName) {
		this.publisherTypeName = publisherTypeName;
	}

	public List<Integer> getImages() {
		if (images == null) {
			images = new ArrayList<>();
		}
		return images;
	}

	public void setImages(List<Integer> images) {
		this.images = images;
	}

	public Integer getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(Integer extraCharges) {
		this.extraCharges = extraCharges;
	}
}
