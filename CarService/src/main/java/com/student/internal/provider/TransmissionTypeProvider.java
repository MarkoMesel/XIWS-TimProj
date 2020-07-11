package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.TransmissionTypeDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapAddTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapDeleteTransmissionTypeRequest;
import com.student.soap.carservice.contract.SoapResponse;

@Component("TransmissionTypeProvider")
public class TransmissionTypeProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public TransmissionTypeProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}
	
	public InternalNamedObjectsResponse getTransmissionTypes() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getTransmissionTypeRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addTransmissionType(SoapAddTransmissionTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		TransmissionTypeDbModel transmissionType = new TransmissionTypeDbModel();

		transmissionType.setName(request.getName());
		unitOfWork.getTransmissionTypeRepo().save(transmissionType);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteTransmissionType(SoapDeleteTransmissionTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		Optional<TransmissionTypeDbModel> transmissionType = unitOfWork.getTransmissionTypeRepo()
				.findById(request.getId());
		if (!transmissionType.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getTransmissionTypeRepo().delete(transmissionType.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
