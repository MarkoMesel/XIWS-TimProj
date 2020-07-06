package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarManufacturerDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapAddManufacturerRequest;
import com.student.soap.carservice.contract.SoapDeleteManufacturerRequest;
import com.student.soap.carservice.contract.SoapResponse;

@Component("ManufacturerProvider")
public class ManufacturerProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public ManufacturerProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}
	
	public InternalNamedObjectsResponse getCarManufacturers() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getCarManufacturerRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addManufacturer(SoapAddManufacturerRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);

		CarManufacturerDbModel manufacturer = new CarManufacturerDbModel();

		manufacturer.setName(request.getName());
		unitOfWork.getCarManufacturerRepo().save(manufacturer);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteManufacturer(SoapDeleteManufacturerRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo().findById(request.getId());
		if (!manufacturer.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getCarManufacturerRepo().delete(manufacturer.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
