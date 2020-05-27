package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.PriceDbModel;

public interface PriceRepo extends CrudRepository<PriceDbModel, Integer> {
}
