package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.FuelTypeDbModel;

public interface FuelTypeRepo extends CrudRepository<FuelTypeDbModel, Integer> {
}
