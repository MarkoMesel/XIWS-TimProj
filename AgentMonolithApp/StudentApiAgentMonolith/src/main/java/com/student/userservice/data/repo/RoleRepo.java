package com.student.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.userservice.data.dal.RoleDbModel;

public interface RoleRepo extends CrudRepository<RoleDbModel, Integer> {
}
