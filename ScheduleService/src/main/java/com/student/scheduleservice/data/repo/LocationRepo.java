package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.LocationDbModel;

public interface LocationRepo extends CrudRepository<LocationDbModel, Integer> {
}
