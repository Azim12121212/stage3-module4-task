package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;

import java.util.List;

public interface CommentControllerInterface extends BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    CommentDtoResponse partialUpdate(Long id, CommentDtoRequest updateRequest);

    List<CommentDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);
}