package com.xiws.agentm.request;

import javax.validation.constraints.NotEmpty;

import com.xiws.agentm.validations.ValidPassword;

public class EditRequestModel {
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	@ValidPassword
	private String password;
	
	@NotEmpty
	private String phone;	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone= phone;
	}	
}
