package com.xiws.agentm.reposchedule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.PriceListDbModel;

public interface PriceListRepo extends CrudRepository<PriceListDbModel, Integer> {
	List<PriceListDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
}
