package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.CarClassDbModel;

public interface CarClassRepo extends CrudRepository<CarClassDbModel, Integer> {
}
