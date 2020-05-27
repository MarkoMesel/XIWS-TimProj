package com.student.data.dal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "User")
public class UserDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private RoleDbModel role;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserPermissionDbModel> userPermissions;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String password;

	private boolean emailVerified;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String phone;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	private StatusDbModel status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public RoleDbModel getRole() {
		return role;
	}

	public void setRole(RoleDbModel role) {
		this.role = role;
	}

	public List<UserPermissionDbModel> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(List<UserPermissionDbModel> userPermissions) {
		this.userPermissions = userPermissions;
	}

	public StatusDbModel getStatus() {
		return status;
	}

	public void setStatus(StatusDbModel status) {
		this.status = status;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
}
