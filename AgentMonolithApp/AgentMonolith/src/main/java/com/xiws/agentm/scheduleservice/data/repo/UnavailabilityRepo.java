package com.xiws.agentm.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.UnavailabilityDbModel;

public interface UnavailabilityRepo extends CrudRepository<UnavailabilityDbModel, Integer> {
	List<UnavailabilityDbModel> findByCarId(int id);
}
