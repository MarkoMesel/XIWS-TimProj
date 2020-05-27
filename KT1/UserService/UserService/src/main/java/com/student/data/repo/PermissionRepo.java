package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.PermissionDbModel;

public interface PermissionRepo extends CrudRepository<PermissionDbModel, Integer> {
}
