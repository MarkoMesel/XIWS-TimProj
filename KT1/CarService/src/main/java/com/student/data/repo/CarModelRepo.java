package com.student.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarModelDbModel;

public interface CarModelRepo extends CrudRepository<CarModelDbModel, Integer> {
	List<CarModelDbModel> findByManufacturerId(Integer manufacturerId);
}
