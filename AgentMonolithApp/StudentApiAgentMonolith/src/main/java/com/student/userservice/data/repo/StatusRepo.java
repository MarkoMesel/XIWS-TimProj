package com.student.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.userservice.data.dal.StatusDbModel;

public interface StatusRepo extends CrudRepository<StatusDbModel, Integer> {
}
