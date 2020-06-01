package com.student.agentservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.agentservice.data.dal.AgentDbModel;

public interface AgentRepo extends CrudRepository<AgentDbModel, Integer> {

	AgentDbModel findById(int id);
}
