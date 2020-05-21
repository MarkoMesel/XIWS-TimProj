package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.UserPermissionDbModel;

public interface UserPermissionRepo extends CrudRepository<UserPermissionDbModel, Integer> {
}
