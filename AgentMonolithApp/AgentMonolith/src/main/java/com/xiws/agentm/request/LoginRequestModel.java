package com.xiws.agentm.request;

import javax.validation.constraints.NotEmpty;

public class LoginRequestModel {
	public LoginRequestModel(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public LoginRequestModel() {
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
