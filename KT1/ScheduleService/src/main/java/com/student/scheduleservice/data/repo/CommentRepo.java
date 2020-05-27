package com.student.scheduleservice.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.student.scheduleservice.data.dal.CommentDbModel;

public interface CommentRepo extends CrudRepository<CommentDbModel, Integer> {
}
