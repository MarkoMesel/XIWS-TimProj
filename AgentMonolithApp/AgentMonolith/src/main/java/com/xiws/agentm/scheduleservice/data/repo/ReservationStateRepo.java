package com.xiws.agentm.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.ReservationStateDbModel;

public interface ReservationStateRepo extends CrudRepository<ReservationStateDbModel, Integer> {
}
