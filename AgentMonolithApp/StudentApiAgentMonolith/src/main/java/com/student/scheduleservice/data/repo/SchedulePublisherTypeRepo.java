package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.SchedulePublisherTypeDbModel;

public interface SchedulePublisherTypeRepo extends CrudRepository<SchedulePublisherTypeDbModel, Integer> {
}
