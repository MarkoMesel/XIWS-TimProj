package com.student.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UnitOfWork")
public class UnitOfWork {

	private CarClassRepo carClassRepo;
	private CarManufacturerRepo carManufacturerRepo;
	private CarModelRepo carModelRepo;
	private CarRepo carRepo;
	private CarImageRepo carImageRepo;
	private FuelTypeRepo fuelTypeRepo;
	private TransmissionTypeRepo transmissionTypeRepo;
	private PublisherTypeRepo publisherTypeRepo;
	private LocationRepo locationRepo;

	@Autowired
	public CarClassRepo getCarClassRepo() {
		return carClassRepo;
	}

	public UnitOfWork(CarClassRepo carClassRepo, CarManufacturerRepo carManufacturerRepo, CarModelRepo carModelRepo,
			CarRepo carRepo, CarImageRepo carImageRepo, FuelTypeRepo fuelTypeRepo,
			TransmissionTypeRepo transmissionTypeRepo, PublisherTypeRepo publisherTypeRepo, LocationRepo locationRepo) {
		super();
		this.carClassRepo = carClassRepo;
		this.carManufacturerRepo = carManufacturerRepo;
		this.carModelRepo = carModelRepo;
		this.carRepo = carRepo;
		this.carImageRepo = carImageRepo;
		this.fuelTypeRepo = fuelTypeRepo;
		this.transmissionTypeRepo = transmissionTypeRepo;
		this.publisherTypeRepo = publisherTypeRepo;
		this.locationRepo = locationRepo;
	}

	public CarManufacturerRepo getCarManufacturerRepo() {
		return carManufacturerRepo;
	}

	public CarModelRepo getCarModelRepo() {
		return carModelRepo;
	}

	public CarRepo getCarRepo() {
		return carRepo;
	}

	public CarImageRepo getCarImageRepo() {
		return carImageRepo;
	}

	public FuelTypeRepo getFuelTypeRepo() {
		return fuelTypeRepo;
	}

	public TransmissionTypeRepo getTransmissionTypeRepo() {
		return transmissionTypeRepo;
	}

	public PublisherTypeRepo getPublisherTypeRepo() {
		return publisherTypeRepo;
	}

	public LocationRepo getLocationRepo() {
		return locationRepo;
	}
}
