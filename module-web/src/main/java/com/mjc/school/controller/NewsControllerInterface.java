package com.mjc.school.controller;

import com.mjc.school.service.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NewsControllerInterface extends BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    NewsDtoResponse partialUpdate(Long id, NewsDtoRequest newsDtoRequest);

    List<NewsDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);

    AuthorDtoResponse getAuthorByNewsId(Long newsId);

    Set<TagDtoResponse> getTagsByNewsId(Long newsId);

    List<CommentDtoResponse> getCommentsByNewsId(Long newsId);

    List<NewsDtoResponse> getNewsByTitle(String title);

    List<NewsDtoResponse> getNewsByContent(String content);
}