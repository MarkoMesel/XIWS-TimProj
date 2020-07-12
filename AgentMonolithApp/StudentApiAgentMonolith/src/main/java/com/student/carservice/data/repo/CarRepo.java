package com.student.carservice.data.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.CarDbModel;

public interface CarRepo extends CrudRepository<CarDbModel, Integer> {
	List<CarDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
    @Override
    List<CarDbModel> findAll();
}
