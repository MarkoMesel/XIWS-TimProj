package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarImageDbModel;

public interface CarImageRepo extends CrudRepository<CarImageDbModel, Integer> {
}
