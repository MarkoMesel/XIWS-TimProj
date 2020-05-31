package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.LocationDbModel;

public interface LocationRepo extends CrudRepository<LocationDbModel, Integer> {
}
