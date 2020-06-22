package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.CarClassDbModel;

public interface CarClassRepo extends CrudRepository<CarClassDbModel, Integer> {
}
