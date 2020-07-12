package com.student.carservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.carservice.data.dal.TransmissionTypeDbModel;

public interface TransmissionTypeRepo extends CrudRepository<TransmissionTypeDbModel, Integer> {
}
