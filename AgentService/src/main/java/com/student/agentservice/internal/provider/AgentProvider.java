package com.student.agentservice.internal.provider;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.agentservice.data.dal.AgentDbModel;
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
		
		Optional<AgentDbModel> agent = unitOfWork.getAgentRepo().findById(request.getAgentId());
		
		if(!agent.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		response.setName(agent.get().getName());
		response.setAddress(agent.get().getAddress());
		response.setLocationId(BigInteger.valueOf(agent.get().getLocationId()));
		response.setTaxId(agent.get().getTaxId());
		response.setId(BigInteger.valueOf(agent.get().getId()));
		
		response.setSuccess(true);
		return response;
	}
	
}
