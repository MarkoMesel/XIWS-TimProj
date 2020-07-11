package com.xiws.agentm.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;

public interface BundleRepo extends CrudRepository<BundleDbModel, Integer> {
	List<BundleDbModel> findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(Integer userId, Integer stateId, Integer publisherId, Integer publisherTypeId);
	List<BundleDbModel> findByStateIdAndPublisherIdAndPublisherTypeId(Integer stateId, Integer publisherId, Integer publisherTypeId);
	List<BundleDbModel> findByUserIdAndStateId(Integer userId, Integer stateId);
	List<BundleDbModel> findByStateId(Integer stateId);
	List<BundleDbModel> findByUserId(Integer userId);
	List<BundleDbModel> findByPublisherIdAndPublisherTypeId(Integer publisherId, Integer publisherTypeId);
}
