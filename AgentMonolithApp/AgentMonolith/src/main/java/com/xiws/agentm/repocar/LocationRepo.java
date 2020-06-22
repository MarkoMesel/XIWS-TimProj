package com.xiws.agentm.repocar;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.LocationDbModel;

public interface LocationRepo extends CrudRepository<LocationDbModel, Integer> {
}
