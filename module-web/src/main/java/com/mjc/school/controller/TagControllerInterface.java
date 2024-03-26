package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

import java.util.List;

public interface TagControllerInterface extends BaseController<TagDtoRequest, TagDtoResponse, Long> {

    TagDtoResponse partialUpdate(Long id, TagDtoRequest updateRequest);

    List<TagDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);

    List<NewsDtoResponse> getNewsByTagId(Long id);

    List<NewsDtoResponse> getNewsByTagName(String name);
}