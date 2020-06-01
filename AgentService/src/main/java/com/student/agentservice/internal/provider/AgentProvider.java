package com.student.agentservice.internal.provider;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.agentservice.data.dal.AgentDbModel;
import com.student.agentservice.data.repo.AgentRepo;
import com.student.agentservice.data.repo.UnitOfWork;
import com.student.agentservice.soap.contract.SoapAgentByIdRequest;
import com.student.agentservice.soap.contract.SoapAgentByIdResponse;

@Component("AgentProvider")
public class AgentProvider {

	private UnitOfWork unitOfWork;
	
	@Autowired
	public AgentProvider(UnitOfWork unitOfWork) {
		super();
		this.unitOfWork = unitOfWork;
	}
	
	public SoapAgentByIdResponse getAgent(SoapAgentByIdRequest request) {
		SoapAgentByIdResponse response = new SoapAgentByIdResponse();
		
		AgentDbModel agent = unitOfWork.getAgentRepo().findById(request.getAgentId());		
		response.setName(agent.getName());
		response.setAddress(agent.getAddress());
		response.setLocationId(BigInteger.valueOf(agent.getLocationId()));
		response.setTaxId(agent.getTaxId());
		response.setId(BigInteger.valueOf(agent.getId()));
		
		response.setSuccess(true);
		return response;
	}
	
}
