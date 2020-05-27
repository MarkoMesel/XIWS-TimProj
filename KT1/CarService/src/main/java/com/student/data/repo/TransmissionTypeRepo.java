package com.student.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.data.dal.TransmissionTypeDbModel;

public interface TransmissionTypeRepo extends CrudRepository<TransmissionTypeDbModel, Integer> {
}
