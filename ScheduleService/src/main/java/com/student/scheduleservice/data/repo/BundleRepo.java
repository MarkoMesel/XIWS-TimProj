package com.student.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.BundleDbModel;

public interface BundleRepo extends CrudRepository<BundleDbModel, Integer> {
	List<BundleDbModel> findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(Integer userId, Integer stateId, Integer publisherId, Integer publisherTypeId);
	List<BundleDbModel> findByUserIdAndStateId(Integer userId, Integer stateId);
}
