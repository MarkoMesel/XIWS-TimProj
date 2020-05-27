package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarClassDbModel;

public interface CarClassRepo extends CrudRepository<CarClassDbModel, Integer> {
}
