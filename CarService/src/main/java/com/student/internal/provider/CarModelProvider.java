package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarManufacturerDbModel;
import com.student.data.dal.CarModelDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapAddCarModelRequest;
import com.student.soap.carservice.contract.SoapDeleteCarModelRequest;
import com.student.soap.carservice.contract.SoapResponse;

@Component("CarModelProvider")
public class CarModelProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public CarModelProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}
	
	public InternalCarModelsResponse getAllCarModels() {
		InternalCarModelsResponse response = new InternalCarModelsResponse();
		unitOfWork.getCarModelRepo().findAll().forEach(carModel -> {
			response.addCarModel(new InternalCarModelsResponse.CarModel(carModel.getId(), carModel.getName(),
					carModel.getCarManufacturer().getId(), carModel.getCarManufacturer().getName()));
		});

		response.setSuccess(true);
		return response;
	}

	public InternalCarModelsResponse getCarModelsByManufacturerId(int manufacturerId) {
		InternalCarModelsResponse response = new InternalCarModelsResponse();

		if (!unitOfWork.getCarManufacturerRepo().findById(manufacturerId).isPresent()) {
			response.setSuccess(false);
			return response;
		}

		unitOfWork.getCarModelRepo().findByManufacturerId(manufacturerId).forEach(carModel -> {
			response.addCarModel(new InternalCarModelsResponse.CarModel(carModel.getId(), carModel.getName(),
					carModel.getCarManufacturer().getId(), carModel.getCarManufacturer().getName()));
		});

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addCarModel(SoapAddCarModelRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		Optional<CarManufacturerDbModel> manufacturer = unitOfWork.getCarManufacturerRepo()
				.findById(request.getManufacturerId());
		if (!manufacturer.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		CarModelDbModel carModel = new CarModelDbModel();

		carModel.setName(request.getName());
		carModel.setCarManufacturer(manufacturer.get());
		unitOfWork.getCarModelRepo().save(carModel);

		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarModel(SoapDeleteCarModelRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		Optional<CarModelDbModel> carModel = unitOfWork.getCarModelRepo().findById(request.getId());
		if (!carModel.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		try {
			unitOfWork.getCarModelRepo().delete(carModel.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}
}
