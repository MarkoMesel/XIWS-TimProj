package com.student.agentservice.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UnitOfWork")
public class UnitOfWork {
	private AgentRepo agentRepo;
	
	
	@Autowired
	public UnitOfWork(AgentRepo agentRepo) {
		super();
		this.agentRepo = agentRepo;
	}
	
	public AgentRepo getAgentRepo() {
		return agentRepo;
	}
	
}
