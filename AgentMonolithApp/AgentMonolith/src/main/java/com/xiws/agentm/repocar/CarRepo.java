package com.xiws.agentm.repocar;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.CarDbModel;

public interface CarRepo extends CrudRepository<CarDbModel, Integer> {
	List<CarDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
    @Override
    List<CarDbModel> findAll();
}
