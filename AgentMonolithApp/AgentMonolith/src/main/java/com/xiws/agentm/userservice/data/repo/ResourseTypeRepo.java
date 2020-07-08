package com.xiws.agentm.userservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.userservice.data.dal.ResourseTypeDbModel;

public interface ResourseTypeRepo extends CrudRepository<ResourseTypeDbModel, Integer> {
}
