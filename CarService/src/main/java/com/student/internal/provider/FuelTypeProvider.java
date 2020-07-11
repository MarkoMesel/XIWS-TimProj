package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.FuelTypeDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapAddFuelTypeRequest;
import com.student.soap.carservice.contract.SoapDeleteFuelTypeRequest;
import com.student.soap.carservice.contract.SoapResponse;

@Component("FuelTypeProvider")
public class FuelTypeProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public FuelTypeProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}

	public InternalNamedObjectsResponse getFuelTypes() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getFuelTypeRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addFuelType(SoapAddFuelTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		FuelTypeDbModel fuelType = new FuelTypeDbModel();

		fuelType.setName(request.getName());
		unitOfWork.getFuelTypeRepo().save(fuelType);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteFuelType(SoapDeleteFuelTypeRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		Optional<FuelTypeDbModel> fuelType = unitOfWork.getFuelTypeRepo().findById(request.getId());
		if (!fuelType.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getFuelTypeRepo().delete(fuelType.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
