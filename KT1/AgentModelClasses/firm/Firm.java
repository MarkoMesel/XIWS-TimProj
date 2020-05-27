package com.siteproj0.demo.firm;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import validations.FieldMatch;
import validations.ValidPassword;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFirmName() {
		return firmName;
	}

	public void setFirmName(int firmName) {
		this.firmName = firmName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
