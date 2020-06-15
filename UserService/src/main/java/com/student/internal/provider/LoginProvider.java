package com.student.internal.provider;

import java.security.SecureRandom;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.student.data.dal.UserDbModel;
import com.student.data.repo.UserRepo;
import com.student.internal.contract.InternalLoginRequest;
import com.student.internal.contract.InternalLoginResponse;
import com.student.jwt.JwtUtil;
import com.student.soap.contract.SoapLoginRequest;
import com.student.soap.contract.SoapLoginResponse;

@Component("LoginProvider")
public class LoginProvider {
	
	private UserRepo userRepo;
	private BCryptPasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;

	@Autowired
	public LoginProvider(UserRepo userRepo, JwtUtil jwtUtil) {
		this.userRepo = userRepo;
		this.jwtUtil = jwtUtil;
		passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
	}
	
	public SoapLoginResponse login(SoapLoginRequest request) 
	{
		SoapLoginResponse response = new SoapLoginResponse();
		
		UserDbModel user = userRepo.findByEmail(request.getEmail());
		
		if(user== null) {
			response.setSuccess(false);
			return response;
		}
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			response.setSuccess(false);
			return response;
		}
		
		response.setToken(jwtUtil.getAuthenticationToken(user));
		response.setId(user.getId());
		response.setRoleId(user.getRole().getId());
		response.setRoleName(user.getRole().getName());
		response.setSuccess(true);
		
		return response;
	}
}
