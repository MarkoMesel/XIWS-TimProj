package com.xiws.agentm.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.PriceListDbModel;

public interface PriceListRepo extends CrudRepository<PriceListDbModel, Integer> {
	List<PriceListDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
}
