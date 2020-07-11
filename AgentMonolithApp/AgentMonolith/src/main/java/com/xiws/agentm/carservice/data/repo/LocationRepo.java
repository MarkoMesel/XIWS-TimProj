package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.LocationDbModel;

public interface LocationRepo extends CrudRepository<LocationDbModel, Integer> {
}
