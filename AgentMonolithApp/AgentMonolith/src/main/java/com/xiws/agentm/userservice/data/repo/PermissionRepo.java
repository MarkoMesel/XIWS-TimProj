package com.xiws.agentm.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.userservice.data.dal.PermissionDbModel;

public interface PermissionRepo extends CrudRepository<PermissionDbModel, Integer> {
}
