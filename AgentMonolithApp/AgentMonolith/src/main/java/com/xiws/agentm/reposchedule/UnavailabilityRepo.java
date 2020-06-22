package com.xiws.agentm.reposchedule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.UnavailabilityDbModel;

public interface UnavailabilityRepo extends CrudRepository<UnavailabilityDbModel, Integer> {
	List<UnavailabilityDbModel> findByCarId(int id);
}
