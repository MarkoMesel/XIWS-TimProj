package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.CarClassDbModel;

public interface CarClassRepo extends CrudRepository<CarClassDbModel, Integer> {
}
