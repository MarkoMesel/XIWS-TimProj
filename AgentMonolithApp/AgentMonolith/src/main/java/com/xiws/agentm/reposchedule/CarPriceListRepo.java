package com.xiws.agentm.reposchedule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.CarPriceListDbModel;

public interface CarPriceListRepo extends CrudRepository<CarPriceListDbModel, Integer> {
	List<CarPriceListDbModel> findByCarId(int id);
}
