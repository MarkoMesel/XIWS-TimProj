package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.TransmissionTypeDbModel;

public interface TransmissionTypeRepo extends CrudRepository<TransmissionTypeDbModel, Integer> {
}
