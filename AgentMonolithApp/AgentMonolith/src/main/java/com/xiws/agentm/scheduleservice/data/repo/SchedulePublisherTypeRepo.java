package com.xiws.agentm.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.SchedulePublisherTypeDbModel;

public interface SchedulePublisherTypeRepo extends CrudRepository<SchedulePublisherTypeDbModel, Integer> {
}
