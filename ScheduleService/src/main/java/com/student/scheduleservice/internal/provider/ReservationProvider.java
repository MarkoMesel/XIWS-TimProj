package com.student.scheduleservice.internal.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.ReservationStateDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.soap.contract.scheduleservice.SoapApproveReservationRequest;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCancelReservationRequest;
import com.student.soap.contract.scheduleservice.SoapPayReservationRequest;
import com.student.soap.contract.scheduleservice.SoapRejectReservationRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsRequest;
import com.student.soap.contract.scheduleservice.SoapReserveRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("ReservationProvider")
public class ReservationProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public ReservationProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}

	public SoapResponse reserve(SoapReserveRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), 7);
		
		ReservationStateDbModel pendingState;
		try {
			pendingState = unitOfWork.getReservationStateRepo().findById(1).get();
		} catch (Exception e) {
			return response;
		}
		
		for(BundleDbModel bundle: bundles) {
			bundle.setState(pendingState);
			unitOfWork.getBundleRepo().save(bundle);
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse approve(SoapApproveReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse reject(SoapRejectReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse cancel(SoapCancelReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse pay(SoapPayReservationRequest request) {
		SoapResponse response = new SoapResponse();
		
		response.setSuccess(true);
		return response;
	}

	public SoapBundlesResponse getBundles(SoapReservationsRequest request) {
		SoapBundlesResponse response = new SoapBundlesResponse();

		response.setSuccess(true);
		return response;
	}
}
