package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.FuelTypeDbModel;

public interface FuelTypeRepo extends CrudRepository<FuelTypeDbModel, Integer> {
}
