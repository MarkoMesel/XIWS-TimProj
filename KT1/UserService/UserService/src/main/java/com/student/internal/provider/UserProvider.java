package com.student.internal.provider;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.student.data.dal.UserDbModel;
import com.student.data.repo.UserRepo;
import com.student.internal.contract.InternalEditRequest;
import com.student.internal.contract.InternalGetResponse;
import com.student.internal.contract.InternalResponse;
import com.student.jwt.AuthenticationTokenParseResult;
import com.student.jwt.JwtUtil;

@Component("UserProvider")
public class UserProvider {
	private UserRepo userRepo;
	private JwtUtil jwtUtil;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserProvider(UserRepo userRepo, JwtUtil jwtUtil) {
		this.userRepo = userRepo;
		this.jwtUtil = jwtUtil;
		passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
	}
	
	public InternalResponse edit(InternalEditRequest request) 
	{
		InternalResponse response = new InternalResponse();
		
		try {
			AuthenticationTokenParseResult parseResult = jwtUtil.parseAuthenticationToken(request.getToken());
			
			if(!parseResult.isValid()) {
				response.setSuccess(false);
				return response;
			}
			Optional<UserDbModel> user = userRepo.findById(parseResult.getUserId());
			if (!user.isPresent()) {
				response.setSuccess(false);
				return response;
			}

			user.get().setFirstName(request.getFirstName());
			user.get().setLastName(request.getLastName());
			user.get().setPhone(request.getPhone());

			if (request.getPassword()!=null && !request.getPassword().isEmpty()) {
				user.get().setPassword(passwordEncoder.encode(request.getPassword()));
			}
			userRepo.save(user.get());
			
			response.setSuccess(true);
			return response;
		} catch (Exception e) {				
			response = new InternalGetResponse();
			response.setSuccess(false);
			return response;
		}
	}
	public InternalGetResponse get(String token) {
		InternalGetResponse response = new InternalGetResponse();
		
		try {
			AuthenticationTokenParseResult parseResult = jwtUtil.parseAuthenticationToken(token);
			
			if(!parseResult.isValid()) {
				response.setSuccess(false);
				return response;
			}
			Optional<UserDbModel> user = userRepo.findById(parseResult.getUserId());
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
}
