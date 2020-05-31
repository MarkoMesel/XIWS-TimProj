package com.student.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.CarDbModel;

public interface CarRepo extends CrudRepository<CarDbModel, Integer> {
	List<CarDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
    @Override
    List<CarDbModel> findAll();
}
