package com.xiws.agentm.reposchedule;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.SchedulePublisherTypeDbModel;

public interface SchedulePublisherTypeRepo extends CrudRepository<SchedulePublisherTypeDbModel, Integer> {
}
