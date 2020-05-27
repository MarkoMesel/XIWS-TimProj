package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarManufacturerDbModel;

public interface CarManufacturerRepo extends CrudRepository<CarManufacturerDbModel, Integer> {
}
