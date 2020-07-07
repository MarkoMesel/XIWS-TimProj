package com.xiws.agentm.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.PriceDbModel;

public interface PriceRepo extends CrudRepository<PriceDbModel, Integer> {
}
