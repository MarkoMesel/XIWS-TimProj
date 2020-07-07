package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.FuelTypeDbModel;

public interface FuelTypeRepo extends CrudRepository<FuelTypeDbModel, Integer> {
}
