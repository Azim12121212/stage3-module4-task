package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;

import java.util.List;

public interface CommentControllerInterface extends BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    List<CommentDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);
}