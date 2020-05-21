package com.student.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;

public interface CarPriceListRepo extends CrudRepository<CarPriceListDbModel, Integer> {
	List<CarPriceListDbModel> findByCarId(int id);
}
