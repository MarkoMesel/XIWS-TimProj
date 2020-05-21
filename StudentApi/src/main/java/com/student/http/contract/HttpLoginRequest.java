package com.student.http.contract;

import javax.validation.constraints.NotEmpty;

public class HttpLoginRequest {
	public HttpLoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public HttpLoginRequest() {
		super();
	}
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
