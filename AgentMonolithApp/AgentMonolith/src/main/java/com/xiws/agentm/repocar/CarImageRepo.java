package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.CarImageDbModel;

public interface CarImageRepo extends CrudRepository<CarImageDbModel, Integer> {
}
