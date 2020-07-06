package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.LocationDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.NamedObject;
import com.student.soap.carservice.contract.SoapAddLocationRequest;
import com.student.soap.carservice.contract.SoapDeleteLocationRequest;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Component("LocationProvider")
public class LocationProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public LocationProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}

	public SoapNamedObjectsResponse getAllLocations() {
		SoapNamedObjectsResponse response = new SoapNamedObjectsResponse();

		unitOfWork.getLocationRepo().findAll().forEach(objectIn -> {
			NamedObject objectOut = new NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.getObject().add(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public SoapResponse addLocation(SoapAddLocationRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		LocationDbModel location = new LocationDbModel();

		location.setName(request.getName());
		unitOfWork.getLocationRepo().save(location);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteLocation(SoapDeleteLocationRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);

		Optional<LocationDbModel> location = unitOfWork.getLocationRepo().findById(request.getId());
		if (!location.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getLocationRepo().delete(location.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
