package com.xiws.agentm.reposchedule;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.MessageDbModel;

public interface MessageRepo extends CrudRepository<MessageDbModel, Integer> {
}
