package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.PublisherTypeDbModel;

public interface PublisherTypeRepo extends CrudRepository<PublisherTypeDbModel, Integer> {
}
