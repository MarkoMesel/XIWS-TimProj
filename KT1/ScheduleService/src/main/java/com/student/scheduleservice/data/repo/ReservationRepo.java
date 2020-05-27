package com.student.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.ReservationDbModel;

public interface ReservationRepo extends CrudRepository<ReservationDbModel, Integer> {
	List<ReservationDbModel> findByCarId(int id);
}
