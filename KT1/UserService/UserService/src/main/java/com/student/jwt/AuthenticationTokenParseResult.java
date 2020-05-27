package com.student.jwt;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationTokenParseResult extends JwtTokenParseResult {
	private String roleName;
	private int roleId;
	private List<Permission> permissions;

	public AuthenticationTokenParseResult() {
		permissions = new ArrayList<>();
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
