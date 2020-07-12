package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.CarImageDbModel;

public interface CarImageRepo extends CrudRepository<CarImageDbModel, Integer> {
}
