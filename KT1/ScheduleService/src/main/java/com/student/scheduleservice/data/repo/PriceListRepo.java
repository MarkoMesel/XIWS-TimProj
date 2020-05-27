package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.PriceListDbModel;

public interface PriceListRepo extends CrudRepository<PriceListDbModel, Integer> {
}
