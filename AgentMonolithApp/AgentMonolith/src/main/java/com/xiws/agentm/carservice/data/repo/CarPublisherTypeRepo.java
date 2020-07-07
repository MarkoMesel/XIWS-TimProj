package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.CarPublisherTypeDbModel;

public interface CarPublisherTypeRepo extends CrudRepository<CarPublisherTypeDbModel, Integer> {
}
