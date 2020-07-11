package com.student.internal.provider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.data.dal.CarDbModel;
import com.student.data.dal.CarImageDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapDeleteImageRequest;
import com.student.soap.carservice.contract.SoapGetImageRequest;
import com.student.soap.carservice.contract.SoapGetImageResponse;
import com.student.soap.carservice.contract.SoapPostImageRequest;
import com.student.soap.carservice.contract.SoapPostImageResponse;
import com.student.soap.carservice.contract.SoapResponse;

@Component("CarImageProvider")
public class CarImageProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public CarImageProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}
	
	public SoapGetImageResponse getCarImage(SoapGetImageRequest request) {
		SoapGetImageResponse response = new SoapGetImageResponse();

		Optional<CarImageDbModel> image = unitOfWork.getCarImageRepo().findById(request.getId());
		if (!image.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		response.setId(request.getId());
		response.setCarId(image.get().getCar().getId());
		response.setImage(image.get().getImage());

		response.setSuccess(true);
		return response;
	}
	
	public SoapPostImageResponse postCarImage(SoapPostImageRequest request) {
		SoapPostImageResponse response = new SoapPostImageResponse();

		Optional<CarDbModel> car = unitOfWork.getCarRepo().findById(request.getCarId());
		if (!car.isPresent()) {
			return new SoapPostImageResponse();
		}

		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, car.get().getPublisherId(), car.get().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		CarImageDbModel image = new CarImageDbModel();

		image.setCar(car.get());
		image.setImage(request.getImage());
		unitOfWork.getCarImageRepo().save(image);

		response.setImageId(image.getId());
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarImage(SoapDeleteImageRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<CarImageDbModel> image = unitOfWork.getCarImageRepo().findById(request.getId());
		if (!image.isPresent()) {
			return new SoapResponse();
		}

		AuthenticationTokenParseResult tokenParseResult = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAutharized(tokenParseResult, 2, image.get().getCar().getPublisherId(),
				image.get().getCar().getPublisherType().getId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		unitOfWork.getCarImageRepo().delete(image.get());

		response.setSuccess(true);
		return response;
	}
}
