package com.xiws.agentm.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.MessageDbModel;

public interface MessageRepo extends CrudRepository<MessageDbModel, Integer> {
}
