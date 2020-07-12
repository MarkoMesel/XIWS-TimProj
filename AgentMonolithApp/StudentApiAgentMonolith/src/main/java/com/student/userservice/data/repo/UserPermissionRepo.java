package com.student.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.userservice.data.dal.UserPermissionDbModel;

public interface UserPermissionRepo extends CrudRepository<UserPermissionDbModel, Integer> {
}
