package com.student.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.dal.UnavailabilityDbModel;

public interface UnavailabilityRepo extends CrudRepository<UnavailabilityDbModel, Integer> {
	UnavailabilityDbModel findByCarId(int id);
}
