package com.xiws.agentm.firm;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import validations.ValidPassword;

public class Firm {

	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty
	private int firmNumber;
	
	@NotEmpty
	private int firmName;
	
	@NotEmpty
	@ValidPassword
	private String password;
	
	@NotEmpty
	@ValidPassword
	private String confirmPassword;

	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String country; 
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String street;
	
	@NotEmpty
	private String phone;
}
