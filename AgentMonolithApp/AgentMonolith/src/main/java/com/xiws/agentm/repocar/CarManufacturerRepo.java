package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.CarManufacturerDbModel;

public interface CarManufacturerRepo extends CrudRepository<CarManufacturerDbModel, Integer> {
}
