package com.xiws.agentm.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;

public interface ReservationRepo extends CrudRepository<ReservationDbModel, Integer> {
	List<ReservationDbModel> findByCarId(int id);
}
