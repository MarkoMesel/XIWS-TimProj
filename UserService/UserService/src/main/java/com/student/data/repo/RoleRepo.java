package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.RoleDbModel;

public interface RoleRepo extends CrudRepository<RoleDbModel, Integer> {
}
