package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.CarPublisherTypeDbModel;

public interface CarPublisherTypeRepo extends CrudRepository<CarPublisherTypeDbModel, Integer> {
}
