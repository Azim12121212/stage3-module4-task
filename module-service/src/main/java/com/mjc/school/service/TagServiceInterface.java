package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

import java.util.List;

public interface TagServiceInterface extends BaseService<TagDtoRequest, TagDtoResponse, Long> {

    List<TagDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);

    List<NewsDtoResponse> getNewsByTagId(Long id);

    List<NewsDtoResponse> getNewsByTagName(String name);
}
