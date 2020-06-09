package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.BundleDbModel;

public interface BundleRepo extends CrudRepository<BundleDbModel, Integer> {
	BundleDbModel findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(Integer userId, Integer stateId, Integer publisherId, Integer publisherTypeId);
}
