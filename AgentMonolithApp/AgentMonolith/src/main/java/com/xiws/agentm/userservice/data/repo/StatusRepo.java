package com.xiws.agentm.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.userservice.data.dal.StatusDbModel;

public interface StatusRepo extends CrudRepository<StatusDbModel, Integer> {
}
