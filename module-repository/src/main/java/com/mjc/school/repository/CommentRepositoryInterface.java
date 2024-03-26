package com.mjc.school.repository;

import com.mjc.school.repository.model.CommentModel;

import java.util.List;

public interface CommentRepositoryInterface extends BaseRepository<CommentModel, Long> {

    CommentModel partialUpdate(Long id, CommentModel entity);

    List<CommentModel> readAll(Integer pageNum, Integer pageSize, String sortBy);
}