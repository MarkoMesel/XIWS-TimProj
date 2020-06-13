package com.student.scheduleservice.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.CommentDbModel;

public interface CommentRepo extends CrudRepository<CommentDbModel, Integer> {
	List<CommentDbModel> findByReservationId(Integer reservationId);

	List<CommentDbModel> findByApproved(Boolean approved);
}
