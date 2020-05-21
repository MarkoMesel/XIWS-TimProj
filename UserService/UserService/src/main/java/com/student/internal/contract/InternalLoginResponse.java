package com.student.internal.contract;

public class InternalLoginResponse extends InternalResponse{
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public InternalLoginResponse(String token, boolean status){
		super(status);
		this.token = token;
	}

	public InternalLoginResponse() {
		super();
	}
}
