package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.MessageDbModel;

public interface MessageRepo extends CrudRepository<MessageDbModel, Integer> {
}
