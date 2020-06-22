package com.xiws.agentm.reposchedule;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.ReservationStateDbModel;

public interface ReservationStateRepo extends CrudRepository<ReservationStateDbModel, Integer> {
}
