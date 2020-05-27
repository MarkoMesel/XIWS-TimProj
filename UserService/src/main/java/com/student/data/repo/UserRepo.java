package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.UserDbModel;

public interface UserRepo extends CrudRepository<UserDbModel, Integer> {
	UserDbModel findByEmail(String email);
}
