package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.FuelTypeDbModel;

public interface FuelTypeRepo extends CrudRepository<FuelTypeDbModel, Integer> {
}
