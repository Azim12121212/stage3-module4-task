package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.List;

public interface AuthorServiceInterface extends BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    List<AuthorDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);

    List<NewsDtoResponse> getNewsByAuthorName(String name);
}
