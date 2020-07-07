package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.CarManufacturerDbModel;

public interface CarManufacturerRepo extends CrudRepository<CarManufacturerDbModel, Integer> {
}
