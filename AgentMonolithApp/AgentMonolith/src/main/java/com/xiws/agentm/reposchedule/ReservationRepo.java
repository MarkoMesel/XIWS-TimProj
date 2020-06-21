package com.xiws.agentm.reposchedule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.ReservationDbModel;

public interface ReservationRepo extends CrudRepository<ReservationDbModel, Integer> {
	List<ReservationDbModel> findByCarId(int id);
}
