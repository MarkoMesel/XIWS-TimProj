package com.xiws.agentm.reposchedule;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.PriceDbModel;

public interface PriceRepo extends CrudRepository<PriceDbModel, Integer> {
}
