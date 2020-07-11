package com.xiws.agentm.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.userservice.data.dal.RoleDbModel;

public interface RoleRepo extends CrudRepository<RoleDbModel, Integer> {
}
