package com.xiws.agentm.repoagent;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalagent.AgentDbModel;

public interface AgentRepo extends CrudRepository<AgentDbModel, Integer> {
}
