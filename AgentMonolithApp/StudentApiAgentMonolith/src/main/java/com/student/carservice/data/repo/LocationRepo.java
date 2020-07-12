package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.LocationDbModel;

public interface LocationRepo extends CrudRepository<LocationDbModel, Integer> {
}
