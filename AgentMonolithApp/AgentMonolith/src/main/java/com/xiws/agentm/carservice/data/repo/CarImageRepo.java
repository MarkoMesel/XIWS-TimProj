package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.CarImageDbModel;

public interface CarImageRepo extends CrudRepository<CarImageDbModel, Integer> {
}
