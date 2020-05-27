package com.student.http.contract;

public class HttpLoginResponse {
	private String token;

	public HttpLoginResponse() {
	}

	public HttpLoginResponse(String token) {
		super();
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
