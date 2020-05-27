package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.ResourseTypeDbModel;

public interface ResourseTypeRepo extends CrudRepository<ResourseTypeDbModel, Integer> {
}
