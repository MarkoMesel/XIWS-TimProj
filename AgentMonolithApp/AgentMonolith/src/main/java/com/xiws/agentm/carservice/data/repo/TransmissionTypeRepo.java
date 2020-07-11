package com.xiws.agentm.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.carservice.data.dal.TransmissionTypeDbModel;

public interface TransmissionTypeRepo extends CrudRepository<TransmissionTypeDbModel, Integer> {
}
