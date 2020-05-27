package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarDbModel;

public interface CarRepo extends CrudRepository<CarDbModel, Integer> {
}
