package com.xiws.agentm.reposchedule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiws.agentm.dalschedule.CommentDbModel;

public interface CommentRepo extends CrudRepository<CommentDbModel, Integer> {
	List<CommentDbModel> findByReservationId(Integer reservationId);

	List<CommentDbModel> findByApproved(Boolean approved);
}
