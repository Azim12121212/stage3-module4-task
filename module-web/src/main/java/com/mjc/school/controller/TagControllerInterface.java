package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagControllerInterface extends BaseController<TagDtoRequest, TagDtoResponse, Long> {

    ResponseEntity<TagDtoResponse> partialUpdate(Long id, TagDtoRequest updateRequest);

    ResponseEntity<List<TagDtoResponse>> readAll(Integer pageNum, Integer pageSize, String sortBy);

    ResponseEntity<List<NewsDtoResponse>> getNewsByTagId(Long id);

    ResponseEntity<List<NewsDtoResponse>> getNewsByTagName(String name);
}