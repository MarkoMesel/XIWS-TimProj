package com.xiws.agentm.repocar;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalcar.CarModelDbModel;

public interface CarModelRepo extends CrudRepository<CarModelDbModel, Integer> {
	List<CarModelDbModel> findByManufacturerId(Integer manufacturerId);
}
