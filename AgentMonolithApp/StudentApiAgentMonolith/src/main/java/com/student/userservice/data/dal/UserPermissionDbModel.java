package com.student.userservice.data.dal;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="UserPermission")
public class UserPermissionDbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private BigInteger unixTimestamp;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="RESOURSE_TYPE_ID")
	private ResourseTypeDbModel resourseType;
	
	private Integer resourseId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PERMISSION_ID")
	private PermissionDbModel permission;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private UserDbModel user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getUnixTimestamp() {
		return unixTimestamp;
	}

	public void setUnixTimestamp(BigInteger unixTimestamp) {
		this.unixTimestamp = unixTimestamp;
	}

	public PermissionDbModel getPermission() {
		return permission;
	}

	public void setPermission(PermissionDbModel permission) {
		this.permission = permission;
	}

	public UserDbModel getUser() {
		return user;
	}

	public void setUser(UserDbModel user) {
		this.user = user;
	}

	public ResourseTypeDbModel getResourseType() {
		return resourseType;
	}

	public void setResourseType(ResourseTypeDbModel resourseType) {
		this.resourseType = resourseType;
	}

	public Integer getResourseId() {
		return resourseId;
	}

	public void setResourseId(Integer resourseId) {
		this.resourseId = resourseId;
	}
}
