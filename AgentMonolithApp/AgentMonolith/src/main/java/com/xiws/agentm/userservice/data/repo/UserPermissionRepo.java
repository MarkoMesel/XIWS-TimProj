package com.xiws.agentm.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.userservice.data.dal.UserPermissionDbModel;

public interface UserPermissionRepo extends CrudRepository<UserPermissionDbModel, Integer> {
}
