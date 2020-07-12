package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.CarManufacturerDbModel;

public interface CarManufacturerRepo extends CrudRepository<CarManufacturerDbModel, Integer> {
}
