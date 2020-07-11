package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarClassDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapAddCarClassRequest;
import com.student.soap.carservice.contract.SoapDeleteCarClassRequest;
import com.student.soap.carservice.contract.SoapResponse;

@Component("CarClassProvider")
public class CarClassProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public CarClassProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}
	
	public InternalNamedObjectsResponse getCarClasses() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getCarClassRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addCarClass(SoapAddCarClassRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		CarClassDbModel carClass = new CarClassDbModel();

		carClass.setName(request.getName());
		unitOfWork.getCarClassRepo().save(carClass);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarClass(SoapDeleteCarClassRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);

		Optional<CarClassDbModel> carClass = unitOfWork.getCarClassRepo().findById(request.getId());
		if (!carClass.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getCarClassRepo().delete(carClass.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
