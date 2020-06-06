package com.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.student.http.contract.HttpEditRequest;
import com.student.http.contract.HttpGetResponse;
import com.student.http.contract.HttpLoginRequest;
import com.student.http.contract.HttpLoginResponse;
import com.student.http.contract.HttpRegisterRequest;
import com.student.internal.translator.Translator;
import com.student.soap.client.UserServiceClient;
import com.student.soap.userservice.contract.SoapActivateUserRequest;
import com.student.soap.userservice.contract.SoapBlockUserRequest;
import com.student.soap.userservice.contract.SoapDeleteUserRequest;
import com.student.soap.userservice.contract.SoapGetResponse;
import com.student.soap.userservice.contract.SoapLoginResponse;
import com.student.soap.userservice.contract.SoapResponse;

@Controller
public class UserController {
	private Translator translator;
	private UserServiceClient userServiceClient;

	@Autowired
	public UserController(UserServiceClient userServiceClient, Translator translator) {
		this.userServiceClient = userServiceClient;
		this.translator = translator;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/user/login")
	@ResponseBody
	public ResponseEntity<HttpLoginResponse> login(@RequestBody HttpLoginRequest request) {
		
		SoapLoginResponse internalResponse= userServiceClient.send(translator.translate(request));
		
		if (!internalResponse.isSuccess()) 
		{ 			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		return new ResponseEntity<>(translator.httpTranslate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/user/register")
	public ResponseEntity<?> registerUserAccount(@RequestBody HttpRegisterRequest request) {		
		SoapResponse internalResponse= userServiceClient.send(translator.translate(request));
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/user/verify/{token}")
	public ResponseEntity<?> verify(@PathVariable String token) {
		SoapResponse internalResponse= userServiceClient.send(translator.translateVerify(token));
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/user/getProfile")
	public ResponseEntity<HttpGetResponse> getProfileInformation(@RequestHeader("token") String securityToken) {
		if (securityToken == null) {
			return new ResponseEntity<HttpGetResponse>(HttpStatus.UNAUTHORIZED);
		}
		
		SoapGetResponse internalResponse= userServiceClient.send(translator.translateGet(securityToken));
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<HttpGetResponse>(translator.translate(internalResponse),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping(path = "/user/editProfile")
	@ResponseBody
	public ResponseEntity<?> editProfile(@RequestHeader("token") String securityToken, @RequestBody HttpEditRequest request) {
		SoapResponse internalResponse= userServiceClient.send(translator.translate(request, securityToken));
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping(path = "/user/{id}/activate")
	@ResponseBody
	public ResponseEntity<?> activateUser(@RequestHeader("token") String token, @PathVariable int id) {
		SoapActivateUserRequest request = new SoapActivateUserRequest();
		
		request.setId(id);
		request.setToken(token);
		
		SoapResponse internalResponse= userServiceClient.send(request);
		
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping(path = "/user/{id}/block")
	@ResponseBody
	public ResponseEntity<?> blockUser(@RequestHeader("token") String token, @PathVariable int id) {
		SoapBlockUserRequest request = new SoapBlockUserRequest();
		
		request.setId(id);
		request.setToken(token);
		
		SoapResponse internalResponse= userServiceClient.send(request);
		
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/user/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteUser(@RequestHeader("token") String token, @PathVariable int id) {
		SoapDeleteUserRequest request = new SoapDeleteUserRequest();
		
		request.setId(id);
		request.setToken(token);
		
		SoapResponse internalResponse= userServiceClient.send(request);
		
		if (internalResponse.isAuthorized()!=null && !internalResponse.isAuthorized()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!internalResponse.isSuccess()) 
		{ 				
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
