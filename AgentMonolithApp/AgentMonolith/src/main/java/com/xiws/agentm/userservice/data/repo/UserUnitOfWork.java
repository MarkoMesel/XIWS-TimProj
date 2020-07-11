package com.xiws.agentm.userservice.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UnitOfWork")
public class UserUnitOfWork {
	private PermissionRepo permissionRepo;
	private ResourseTypeRepo resourseTypeRepo;
	private RoleRepo roleRepo;
	private StatusRepo statusRepo;
	private UserPermissionRepo userPermissionRepo;
	private UserRepo userRepo;
	
	@Autowired
	public UserUnitOfWork(PermissionRepo permissionRepo, ResourseTypeRepo resourseTypeRepo, RoleRepo roleRepo,
			StatusRepo statusRepo, UserPermissionRepo userPermissionRepo, UserRepo userRepo) {
		super();
		this.permissionRepo = permissionRepo;
		this.resourseTypeRepo = resourseTypeRepo;
		this.roleRepo = roleRepo;
		this.statusRepo = statusRepo;
		this.userPermissionRepo = userPermissionRepo;
		this.userRepo = userRepo;
	}

	public PermissionRepo getPermissionRepo() {
		return permissionRepo;
	}

	public ResourseTypeRepo getResourseTypeRepo() {
		return resourseTypeRepo;
	}

	public RoleRepo getRoleRepo() {
		return roleRepo;
	}

	public StatusRepo getStatusRepo() {
		return statusRepo;
	}

	public UserPermissionRepo getUserPermissionRepo() {
		return userPermissionRepo;
	}

	public UserRepo getUserRepo() {
		return userRepo;
	}
}
