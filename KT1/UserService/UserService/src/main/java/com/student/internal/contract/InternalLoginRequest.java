package com.student.internal.contract;

import javax.validation.constraints.NotEmpty;

public class InternalLoginRequest{
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

	public InternalLoginRequest() {
		super();
	}

	public InternalLoginRequest(@NotEmpty String email, @NotEmpty String password) {
		super();
		this.email = email;
		this.password = password;
	}
}
