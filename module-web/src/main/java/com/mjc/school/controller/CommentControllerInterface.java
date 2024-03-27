package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentControllerInterface extends BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    ResponseEntity<CommentDtoResponse> partialUpdate(Long id, CommentDtoRequest updateRequest);

    ResponseEntity<List<CommentDtoResponse>> readAll(Integer pageNum, Integer pageSize, String sortBy);
}