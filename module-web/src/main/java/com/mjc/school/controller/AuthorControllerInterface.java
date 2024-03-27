package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorControllerInterface extends BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    ResponseEntity<AuthorDtoResponse> partialUpdate(Long id, AuthorDtoRequest updateRequest);

    ResponseEntity<List<AuthorDtoResponse>> readAll(Integer pageNum, Integer pageSize, String sortBy);

    ResponseEntity<List<NewsDtoResponse>> getNewsByAuthorName(String name);
}