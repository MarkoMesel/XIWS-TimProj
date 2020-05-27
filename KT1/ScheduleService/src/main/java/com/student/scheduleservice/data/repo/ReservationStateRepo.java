package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.ReservationStateDbModel;

public interface ReservationStateRepo extends CrudRepository<ReservationStateDbModel, Integer> {
}
