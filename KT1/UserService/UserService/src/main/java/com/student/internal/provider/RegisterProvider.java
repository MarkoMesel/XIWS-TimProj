package com.student.internal.provider;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.student.data.dal.UserDbModel;
import com.student.data.repo.RoleRepo;
import com.student.data.repo.StatusRepo;
import com.student.data.repo.UserRepo;
import com.student.internal.contract.InternalRegisterRequest;
import com.student.internal.contract.InternalResponse;
import com.student.jwt.JwtTokenParseResult;
import com.student.jwt.JwtUtil;

@Component("RegisterProvider")
public class RegisterProvider {
	private UserRepo userRepo;
	private RoleRepo roleRepo;
	private StatusRepo statusRepo;
	private BCryptPasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;

	@Autowired
	public RegisterProvider(UserRepo userRepo, JwtUtil jwtUtil, RoleRepo roleRepo, StatusRepo statusRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.statusRepo = statusRepo;
		this.jwtUtil = jwtUtil;
		passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
	}

	public InternalResponse register(InternalRegisterRequest request) {
		InternalResponse response = new InternalResponse();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<InternalRegisterRequest>> constraintViolations = validator.validate(request);

		if (!constraintViolations.isEmpty()) {

			System.out.print(constraintViolations);

			response.setSuccess(false);
			return response;
		}

		UserDbModel userDbModel = new UserDbModel();
		userDbModel.setFirstName(request.getFirstName());
		userDbModel.setLastName(request.getLastName());
		userDbModel.setEmail(request.getEmail());
		userDbModel.setPhone(request.getPhone());
		userDbModel.setRole(roleRepo.findById(1).get());
		userDbModel.setPassword(passwordEncoder.encode(request.getPassword()));
		userDbModel.setStatus(statusRepo.findById(1).get());

		userRepo.save(userDbModel);

		System.out.println("Verify user link: http://localhost:8081/user/verify/"
				+ jwtUtil.getEmailVerificationToken(userDbModel.getId()));

		response.setSuccess(true);
		return response;
	}

	public InternalResponse verify(String token) {
		InternalResponse response = new InternalResponse();

		JwtTokenParseResult parseResult = jwtUtil.parseEmailVarificationToken(token);
		if (!parseResult.isValid()) {
			response.setSuccess(false);
			return response;
		}

		Optional<UserDbModel> user = userRepo.findById(parseResult.getUserId());
		if (!user.isPresent() || user.get().isEmailVerified()) {
			response.setSuccess(false);
			return response;
		};
		user.get().setEmailVerified(true);
		userRepo.save(user.get());

		response.setSuccess(true);
		return response;
	}
}
