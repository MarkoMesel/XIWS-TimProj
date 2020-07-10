package com.xiws.agentm.controller;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiws.agentm.userservice.data.dal.UserPermissionDbModel;
//import com.student.internal.contract.InternalGetResponse;
//import com.student.internal.contract.InternalRegisterRequest;
//import com.student.internal.contract.InternalResponse;
//import com.student.jwt.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;
import com.xiws.agentm.request.EditRequestModel;
import com.xiws.agentm.request.LoginRequestModel;
import com.xiws.agentm.request.RegisterRequestModel;
import com.xiws.agentm.response.GetResponseModel;
import com.xiws.agentm.response.LoginResponseModel;
import com.xiws.agentm.userservice.data.dal.UserDbModel;
import com.xiws.agentm.userservice.data.repo.RoleRepo;
import com.xiws.agentm.userservice.data.repo.StatusRepo;
import com.xiws.agentm.userservice.data.repo.UserRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.internal.contract.InternalGetResponse;
//import com.student.internal.contract.InternalResponse;
import com.xiws.agentm.AuthenticationTokenParseResult;
//import com.student.soap.contract.SoapLoginResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//import com.student.http.contract.HttpEditRequest;
//import com.student.http.contract.HttpGetResponse;
//import com.student.http.contract.HttpLoginRequest;
//import com.student.http.contract.HttpLoginResponse;
//import com.student.http.contract.HttpRegisterRequest;
//import com.student.http.contract.HttpUserResponse;
//import com.student.internal.translator.Translator;
//import com.student.soap.client.UserServiceClient;
//import com.student.soap.contract.userservice.SoapActivateUserRequest;
//import com.student.soap.contract.userservice.SoapBlockUserRequest;
//import com.student.soap.contract.userservice.SoapDeleteUserRequest;
//import com.student.soap.contract.userservice.SoapGetResponse;
//import com.student.soap.contract.userservice.SoapLoginResponse;
//import com.student.soap.contract.userservice.SoapResponse;
//import com.student.soap.contract.userservice.SoapUsersRequest;
//import com.student.soap.contract.userservice.SoapUsersResponse;

@Controller
public class UserController {
	
	private BCryptPasswordEncoder passwordEncoder;
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	StatusRepo statusRepo;
	
//	private Translator translator;
//	private UserServiceClient userServiceClient;
//
//	@Autowired
//	public UserController(UserServiceClient userServiceClient, Translator translator) {
//		this.userServiceClient = userServiceClient;
//		this.translator = translator;
//	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/user/login")
	@ResponseBody
	public ResponseEntity<LoginResponseModel> login(@RequestBody LoginRequestModel request) {
		
//		SoapLoginResponse internalResponse= userServiceClient.send(translator.translate(request));
		
		LoginResponseModel response = new LoginResponseModel();
		
		UserDbModel user = userRepo.findByEmail(request.getEmail());
		
		if(user== null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(user.getStatus().getId() != 2) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		response.setToken(getAuthenticationToken(user));
		response.setId(user.getId());
		response.setRoleId(user.getRole().getId());
		response.setRoleName(user.getRole().getName());
		//response.setSuccess(true);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
//		if (!internalResponse.isSuccess()) 
//		{ 			
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			
//		}
//		
//		return new ResponseEntity<>(translator.httpTranslate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/user/register")
	public ResponseEntity<?> registerUserAccount(@RequestBody RegisterRequestModel request) {		
//		SoapResponse internalResponse= userServiceClient.send(translator.translate(request));
	
//		InternalResponse response = new InternalResponse();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<RegisterRequestModel>> constraintViolations = validator.validate(request);

		if (!constraintViolations.isEmpty()) {

			System.out.print(constraintViolations);

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
		
//		System.out.println("Verify user link: http://localhost:8081/user/verify/"
//				+ jwtUtil.getEmailVerificationToken(userDbModel.getId()));
				
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@GetMapping(path = "/user/verify/{token}")
//	public ResponseEntity<?> verify(@PathVariable String token) {
//		SoapResponse internalResponse= userServiceClient.send(translator.translateVerify(token));
//		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/user/getProfile")
	public ResponseEntity<GetResponseModel> getProfileInformation(@RequestHeader("token") String securityToken) {
		if (securityToken == null) {
			return new ResponseEntity<GetResponseModel>(HttpStatus.UNAUTHORIZED);
		}
		
//		SoapGetResponse internalResponse= userServiceClient.send(translator.translateGet(securityToken));

		GetResponseModel response = new GetResponseModel();

		try {
			AuthenticationTokenParseResult parseResult = parseAuthenticationToken(securityToken);

			if (!parseResult.isValid()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Optional<UserDbModel> user = userRepo.findById(parseResult.getUserId());
			if (!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			GetResponseModel internalResponse = new GetResponseModel();
			internalResponse.setEmail(user.get().getEmail());
			internalResponse.setPhone(user.get().getPhone());
			internalResponse.setFirstName(user.get().getFirstName());
			internalResponse.setLastName(user.get().getLastName());
			return new ResponseEntity<GetResponseModel>(internalResponse,HttpStatus.OK);

			//return result;
		} catch (Exception e) {
			response = new GetResponseModel();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<HttpGetResponse>(translator.translate(internalResponse),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping(path = "/user/editProfile")
	@ResponseBody
	public ResponseEntity<?> editProfile(@RequestHeader("token") String securityToken, @RequestBody EditRequestModel request) {
//		SoapResponse internalResponse= userServiceClient.send(translator.translate(request, securityToken));

//		InternalResponse response = new InternalResponse();

		try {
			AuthenticationTokenParseResult parseResult = parseAuthenticationToken(securityToken);

			if (!parseResult.isValid()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Optional<UserDbModel> user = userRepo.findById(parseResult.getUserId());
			if (!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			user.get().setFirstName(request.getFirstName());
			user.get().setLastName(request.getLastName());
			user.get().setPhone(request.getPhone());

			if (request.getPassword() != null && !request.getPassword().isEmpty()) {
				user.get().setPassword(passwordEncoder.encode(request.getPassword()));
			}
			userRepo.save(user.get());

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@PutMapping(path = "/user/{id}/activate")
//	@ResponseBody
//	public ResponseEntity<?> activateUser(@RequestHeader("token") String token, @PathVariable int id) {
//		SoapActivateUserRequest request = new SoapActivateUserRequest();
//		
//		request.setId(id);
//		request.setToken(token);
//		
//		SoapResponse internalResponse= userServiceClient.send(request);
//		
//		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@PutMapping(path = "/user/{id}/block")
//	@ResponseBody
//	public ResponseEntity<?> blockUser(@RequestHeader("token") String token, @PathVariable int id) {
//		SoapBlockUserRequest request = new SoapBlockUserRequest();
//		
//		request.setId(id);
//		request.setToken(token);
//		
//		SoapResponse internalResponse= userServiceClient.send(request);
//		
//		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@DeleteMapping(path = "/user/{id}")
//	@ResponseBody
//	public ResponseEntity<?> deleteUser(@RequestHeader("token") String token, @PathVariable int id) {
//		SoapDeleteUserRequest request = new SoapDeleteUserRequest();
//		
//		request.setId(id);
//		request.setToken(token);
//		
//		SoapResponse internalResponse= userServiceClient.send(request);
//		
//		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@GetMapping(path = "/user/all")
//	@ResponseBody
//	public ResponseEntity<List<HttpUserResponse>> getAllUsers(@RequestHeader("token") String token) {
//		SoapUsersRequest request = new SoapUsersRequest();
//		request.setToken(token);
//		
//		SoapUsersResponse internalResponse= userServiceClient.send(request);
//		
//		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//		
//		if (!internalResponse.isSuccess()) 
//		{ 				
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<List<HttpUserResponse>>(translator.translate(internalResponse), HttpStatus.OK);
//	}
	
	public String getAuthenticationToken(UserDbModel userDbModel) {
		List<Permission> permissions = new ArrayList<Permission>();
		for (UserPermissionDbModel userPermission : userDbModel.getUserPermissions()) {
			Permission permission = new Permission();
			permission.setPermissionId(userPermission.getPermission().getId());
			permission.setPermissionName(userPermission.getPermission().getName());
			if(userPermission.getResourseType() != null) {
				permission.setResourceTypeId(userPermission.getResourseType().getId());
				permission.setResourceTypeName(userPermission.getResourseType().getName());
				permission.setResourceId(userPermission.getResourseId());
			}
			permissions.add(permission);
		}
		return Jwts.builder().setIssuer(issuer)
				.claim("roleName", userDbModel.getRole().getName())
				.claim("roleId", userDbModel.getRole().getId())
				.claim("permissions", permissions)
				.claim("userId", userDbModel.getId())
				.claim("purpose", AUTHENTICATION_STRING)
				// RS256 with privateKey
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}
	
	public AuthenticationTokenParseResult parseAuthenticationToken(String token) {
		AuthenticationTokenParseResult result = new AuthenticationTokenParseResult();
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));
			Object permissionsObject = claims.get("permissions");
			ObjectMapper mapper = new ObjectMapper();
			List<Permission> permissions= mapper.convertValue(permissionsObject, new TypeReference<List<Permission>>() { });
			result.setPermissions(permissions);
			result.setRoleName((String) claims.get("roleName"));
			result.setRoleId((Integer) claims.get("roleId"));

			if (result.getIssuer() == null || !result.getIssuer().equals(issuer) || result.getUserId() == null
					|| result.getPurpose() == null || !result.getPurpose().equals(AUTHENTICATION_STRING)
					|| (!(result.getRoleName().equals("BASIC") && result.getRoleId() == 1)
							&& !(result.getRoleName().equals("AGENT") && result.getRoleId() == 2)
							&& !(result.getRoleName().equals("ADMIN") && result.getRoleId() == 3))) {
				result = new AuthenticationTokenParseResult();
				result.setValid(false);
				return result;
			}
			result.setValid(true);
			return result;
		} catch (Exception e) {
			result = new AuthenticationTokenParseResult();
			result.setValid(false);
			return result;
		}
	}
}
