package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.StatusDbModel;

public interface StatusRepo extends CrudRepository<StatusDbModel, Integer> {
}
