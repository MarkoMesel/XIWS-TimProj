
package com.siteproj0.demo.firm;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import validations.ValidPassword;

@Entity
@Table(name="Firm")
public class FirmDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private int firmNumber;
	
	@NotEmpty
	private int firmName;
	
	@NotEmpty
	@ValidPassword
	private String password;

	private boolean isVerified;
	
	private UUID validationToken;
	
	private UUID securityToken;

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

	public int getFirmNumber() {
		return firmNumber;
	}

	public void setFirmNumber(int firmNumber) {
		this.firmNumber = firmNumber;
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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public UUID getValidationToken() {
		return validationToken;
	}

	public void setValidationToken(UUID validationToken) {
		this.validationToken = validationToken;
	}

	public UUID getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(UUID securityToken) {
		this.securityToken = securityToken;
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
