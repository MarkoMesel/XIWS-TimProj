package com.student.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.userservice.data.dal.ResourseTypeDbModel;

public interface ResourseTypeRepo extends CrudRepository<ResourseTypeDbModel, Integer> {
}
