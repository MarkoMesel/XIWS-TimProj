package com.student.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarImageDbModel;

public interface CarImageRepo extends CrudRepository<CarImageDbModel, Integer> {
	@Override
	List<CarImageDbModel> findAll();
}
