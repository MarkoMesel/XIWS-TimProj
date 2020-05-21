package com.student.internal.translator;

import org.springframework.stereotype.Component;

import com.student.internal.contract.InternalLoginRequest;
import com.student.internal.contract.InternalLoginResponse;
import com.student.internal.contract.InternalRegisterRequest;
import com.student.soap.contract.SoapLoginRequest;
import com.student.soap.contract.SoapLoginResponse;
import com.student.soap.contract.SoapRegisterRequest;
import com.student.soap.contract.SoapResponse;

@Component("Translator")
public class Translator {

	public InternalLoginRequest translate(SoapLoginRequest input)
	{
		 InternalLoginRequest output = new InternalLoginRequest();
		 output.setEmail(input.getEmail());
		 output.setPassword(input.getPassword());
		return output;
	}

	public SoapLoginResponse soapTranslate(InternalLoginResponse input)
	{
		SoapLoginResponse output = new SoapLoginResponse();
		output.setSuccess(input.isSuccess());
		output.setToken(input.getToken().toString());
		return output;
	}
	
	public InternalRegisterRequest translate(SoapRegisterRequest input)
	{
		InternalRegisterRequest output = new InternalRegisterRequest();
		output.setEmail(input.getEmail());
		output.setPassword(input.getPassword());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPhone(input.getPhone());
		return output;
	}
	
	public com.student.internal.contract.InternalEditRequest translate(com.student.soap.contract.SoapEditRequest input)
	{
		com.student.internal.contract.InternalEditRequest output = new com.student.internal.contract.InternalEditRequest();
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setPassword(input.getPassword());
		output.setPhone(input.getPhone());
		output.setToken(input.getToken());
		return output;
	}
	
	public com.student.soap.contract.SoapGetResponse soapTranslate(com.student.internal.contract.InternalGetResponse input)
	{
		com.student.soap.contract.SoapGetResponse output = new com.student.soap.contract.SoapGetResponse();
		output.setSuccess(input.isSuccess());
		output.setEmail(input.getEmail());
		output.setPhone(input.getPhone());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		return output;
	}
	
	public SoapResponse translate(com.student.internal.contract.InternalResponse input)
	{
		SoapResponse output = new SoapResponse();
		output.setSuccess(input.isSuccess());
		return output;
	}
}
