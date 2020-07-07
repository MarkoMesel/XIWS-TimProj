package com.xiws.agentm.agentservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.agentservice.data.dal.AgentDbModel;

public interface AgentRepo extends CrudRepository<AgentDbModel, Integer> {
}
