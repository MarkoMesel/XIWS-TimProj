package com.student.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.UserDbModel;

public interface UserRepo extends CrudRepository<UserDbModel, Integer> {
	UserDbModel findByEmail(String email);
	@Override
	List<UserDbModel> findAll();
}
