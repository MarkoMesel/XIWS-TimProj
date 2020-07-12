package com.student.carservice.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Car")
public class CarDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean active;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="MODEL_ID")
	private CarModelDbModel carModel;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FUELTYPE_ID")
	private FuelTypeDbModel fuelType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TRANSMISSIONTYPE_ID")
	private TransmissionTypeDbModel transmissionType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CARCLASS_ID")
	private CarClassDbModel carClass;
	
	private int publisherId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LOCATION_ID")
	private LocationDbModel location;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PUBLISHER_TYPE_ID")
	private CarPublisherTypeDbModel publisherType;
	
	@OneToMany(mappedBy = "car", fetch=FetchType.EAGER)
	private List<CarImageDbModel> images;
	
	private int mileage;
	
	private int childSeats;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CarModelDbModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModelDbModel carModel) {
		this.carModel = carModel;
	}

	public FuelTypeDbModel getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelTypeDbModel fuelType) {
		this.fuelType = fuelType;
	}

	public TransmissionTypeDbModel getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionTypeDbModel transmissionType) {
		this.transmissionType = transmissionType;
	}

	public CarClassDbModel getCarClass() {
		return carClass;
	}

	public void setCarClass(CarClassDbModel carClass) {
		this.carClass = carClass;
	}

	public List<CarImageDbModel> getImages() {
		return images;
	}

	public void setImages(List<CarImageDbModel> images) {
		this.images = images;
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

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public CarPublisherTypeDbModel getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(CarPublisherTypeDbModel publisherType) {
		this.publisherType = publisherType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocationDbModel getLocation() {
		return location;
	}

	public void setLocation(LocationDbModel location) {
		this.location = location;
	}
}
