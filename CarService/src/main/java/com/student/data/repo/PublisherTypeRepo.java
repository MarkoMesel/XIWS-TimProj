package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.PublisherTypeDbModel;

public interface PublisherTypeRepo extends CrudRepository<PublisherTypeDbModel, Integer> {
}
