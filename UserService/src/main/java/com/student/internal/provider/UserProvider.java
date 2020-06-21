package com.student.internal.provider;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.student.data.dal.StatusDbModel;
import com.student.data.dal.UserDbModel;
import com.student.data.repo.UnitOfWork;
import com.student.internal.contract.InternalEditRequest;
import com.student.internal.contract.InternalGetResponse;
import com.student.internal.contract.InternalResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;
import com.student.soap.carservice.contract.SoapDeactivatePublisherRequest;
import com.student.soap.client.CarServiceClient;
import com.student.soap.contract.SoapActivateUserRequest;
import com.student.soap.contract.SoapBlockUserRequest;
import com.student.soap.contract.SoapDeleteUserRequest;
import com.student.soap.contract.SoapGetResponse;
import com.student.soap.contract.SoapInternalGetUserRequest;
import com.student.soap.contract.SoapResponse;
import com.student.soap.contract.SoapUser;
import com.student.soap.contract.SoapUsersRequest;
import com.student.soap.contract.SoapUsersResponse;

@Component("UserProvider")
public class UserProvider {
	private UnitOfWork unitOfWork;
	private CarServiceClient carServiceClient;
	private JwtUtil jwtUtil;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, CarServiceClient carServiceClient) {
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.carServiceClient = carServiceClient;
		
		passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
	}

	public InternalResponse edit(InternalEditRequest request) {
		InternalResponse response = new InternalResponse();

		try {
			AuthenticationTokenParseResult parseResult = jwtUtil.parseAuthenticationToken(request.getToken());

			if (!parseResult.isValid()) {
				response.setSuccess(false);
				return response;
			}
			Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(parseResult.getUserId());
			if (!user.isPresent()) {
				response.setSuccess(false);
				return response;
			}

			user.get().setFirstName(request.getFirstName());
			user.get().setLastName(request.getLastName());
			user.get().setPhone(request.getPhone());

			if (request.getPassword() != null && !request.getPassword().isEmpty()) {
				user.get().setPassword(passwordEncoder.encode(request.getPassword()));
			}
			unitOfWork.getUserRepo().save(user.get());

			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			response = new InternalGetResponse();
			response.setSuccess(false);
			return response;
		}
	}
	
	public SoapUsersResponse getListOfUsers(SoapUsersRequest request) {
		SoapUsersResponse response = new SoapUsersResponse();
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		
		List<UserDbModel> users = unitOfWork.getUserRepo().findAll();
		
		for(UserDbModel in : users) {
			SoapUser out = new SoapUser();
			out.setId(in.getId());
			out.setEmail(in.getEmail());
			out.setFirstName(in.getFirstName());
			out.setLastName(in.getLastName());
			out.setEmailVerified(in.isEmailVerified());
			out.setPhone(in.getPhone());
			out.setStatusId(in.getStatus().getId());
			out.setStatusName(in.getStatus().getName());
			out.setRoleId(in.getRole().getId());
			out.setRoleName(in.getRole().getName());
			
			response.getUser().add(out);
		}
		
		response.setSuccess(true);
		return response;
	}

	public InternalGetResponse get(String token) {
		InternalGetResponse response = new InternalGetResponse();

		try {
			AuthenticationTokenParseResult parseResult = jwtUtil.parseAuthenticationToken(token);

			if (!parseResult.isValid()) {
				response.setSuccess(false);
				return response;
			}
			Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(parseResult.getUserId());
			if (!user.isPresent()) {
				response.setSuccess(false);
				return response;
			}

			InternalGetResponse result = new InternalGetResponse();
			result.setEmail(user.get().getEmail());
			result.setPhone(user.get().getPhone());
			result.setFirstName(user.get().getFirstName());
			result.setLastName(user.get().getLastName());
			result.setSuccess(true);

			return result;
		} catch (Exception e) {
			response = new InternalGetResponse();
			response.setSuccess(false);
			return response;
		}
	}

	public SoapGetResponse get(SoapInternalGetUserRequest request) {
		SoapGetResponse response = new SoapGetResponse();

		Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(request.getId());
		if (!user.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		response.setEmail(user.get().getEmail());
		response.setPhone(user.get().getPhone());
		response.setFirstName(user.get().getFirstName());
		response.setLastName(user.get().getLastName());
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse blockUser(SoapBlockUserRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(request.getId());
		if(!user.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		Optional<StatusDbModel> blockedStatus = unitOfWork.getStatusRepo().findById(3);
		
		user.get().setStatus(blockedStatus.get());
		
		try {
			unitOfWork.getUserRepo().save(user.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse activateUser(SoapActivateUserRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(request.getId());
		if(!user.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		Optional<StatusDbModel> activeStatus = unitOfWork.getStatusRepo().findById(2);
		
		user.get().setStatus(activeStatus.get());
		
		try {
			unitOfWork.getUserRepo().save(user.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse deleteUser(SoapDeleteUserRequest request) {
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if(!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<UserDbModel> user = unitOfWork.getUserRepo().findById(request.getId());
		if(!user.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			SoapDeactivatePublisherRequest carServiceRequest = new SoapDeactivatePublisherRequest();
			carServiceRequest.setPublisherId(user.get().getId());
			carServiceRequest.setPublisherTypeId(1);
			com.student.soap.carservice.contract.SoapResponse carServiceResponse = carServiceClient.send(carServiceRequest);
			
			if(!carServiceResponse.isSuccess()) {
				response.setSuccess(false);
				return response;
			}
			
			//Change after KT2: soft delete ====================
			//unitOfWork.getUserRepo().delete(user.get());
			
			Optional<StatusDbModel> deletedStatus = unitOfWork.getStatusRepo().findById(4);
			
			user.get().setStatus(deletedStatus.get());
			
			unitOfWork.getUserRepo().save(user.get());
			
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
}
