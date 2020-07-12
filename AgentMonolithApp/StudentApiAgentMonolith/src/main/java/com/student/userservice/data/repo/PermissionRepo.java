package com.student.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.userservice.data.dal.PermissionDbModel;

public interface PermissionRepo extends CrudRepository<PermissionDbModel, Integer> {
}
