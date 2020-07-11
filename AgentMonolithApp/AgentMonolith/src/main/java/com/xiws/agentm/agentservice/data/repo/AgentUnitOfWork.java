package com.xiws.agentm.agentservice.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AgentUnitOfWork")
public class AgentUnitOfWork {
	private AgentRepo agentRepo;
	
	
	@Autowired
	public AgentUnitOfWork(AgentRepo agentRepo) {
		super();
		this.agentRepo = agentRepo;
	}
	
	public AgentRepo getAgentRepo() {
		return agentRepo;
	}
	
}
