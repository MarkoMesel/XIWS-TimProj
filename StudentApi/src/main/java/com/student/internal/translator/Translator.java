package com.student.internal.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.student.http.contract.HttpEditRequest;
import com.student.http.contract.HttpGetResponse;
import com.student.http.contract.HttpLoginRequest;
import com.student.http.contract.HttpLoginResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.http.contract.HttpRegisterRequest;
import com.student.soap.scheduleservice.contract.SoapCarRatingRequest;
import com.student.soap.scheduleservice.contract.SoapNamedObjectsResponse;
import com.student.soap.userservice.contract.SoapEditRequest;
import com.student.soap.userservice.contract.SoapGetRequest;
import com.student.soap.userservice.contract.SoapGetResponse;
import com.student.soap.userservice.contract.SoapLoginRequest;
import com.student.soap.userservice.contract.SoapLoginResponse;
import com.student.soap.userservice.contract.SoapRegisterRequest;
import com.student.soap.userservice.contract.SoapVerifyRequest;

@Component("Translator")
public class Translator {
	//Login
	public SoapLoginRequest translate(HttpLoginRequest input)
	{
		SoapLoginRequest output = new SoapLoginRequest();
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		return output;
	}
	
	public HttpLoginResponse httpTranslate(SoapLoginResponse input)
	{
		HttpLoginResponse output = new HttpLoginResponse();
		output.setToken(input.getToken());
		return output;
	}
	
	//Register
	public SoapRegisterRequest translate(HttpRegisterRequest input)
	{
		SoapRegisterRequest output = new SoapRegisterRequest();
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPhone(input.getPhone());
		return output;
	}
	
	
	//Edit
	public SoapEditRequest translate(HttpEditRequest input, String token)
	{
		SoapEditRequest output = new SoapEditRequest();
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPassword(input.getPassword());
		output.setPhone(input.getPhone());
		output.setToken(token);
		return output;
	}
	
	//Get
	public SoapGetRequest translateGet(String token)
	{
		SoapGetRequest output = new SoapGetRequest();
		output.setToken(token);
		return output;
	}
	
	public HttpGetResponse translate(SoapGetResponse input)
	{
		HttpGetResponse output = new HttpGetResponse();
		output.setEmail(input.getEmail());
		output.setPhone(input.getPhone());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		return output;
	}
	
	//Verify
	public SoapVerifyRequest translateVerify(String token)
	{
		SoapVerifyRequest output = new SoapVerifyRequest();
		output.setToken(token);
		return output;
	}
	
	//Generic
	public List<HttpNamedObjectResponse> translate(SoapNamedObjectsResponse input) {
		List<HttpNamedObjectResponse> output = new ArrayList<>();

		for (SoapNamedObjectsResponse.NamedObjects.NamedObject objectIn : input.getNamedObjects().getNamedObject()) {
			HttpNamedObjectResponse objectOut = new HttpNamedObjectResponse();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.add(objectOut);
		}
		return output;
	}

	public SoapCarRatingRequest translateCarRating(int id) {
		SoapCarRatingRequest output = new SoapCarRatingRequest();
		output.setId(id);
		
		return output;
	}
}
