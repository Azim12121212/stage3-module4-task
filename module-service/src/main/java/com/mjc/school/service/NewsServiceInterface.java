package com.mjc.school.service;

import com.mjc.school.service.dto.*;

import java.util.List;
import java.util.Set;

public interface NewsServiceInterface extends BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    List<NewsDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy);

    AuthorDtoResponse getAuthorByNewsId(Long newsId);

    Set<TagDtoResponse> getTagsByNewsId(Long newsId);

    List<CommentDtoResponse> getCommentsByNewsId(Long newsId);

    List<NewsDtoResponse> getNewsByTitle(String title);

    List<NewsDtoResponse> getNewsByContent(String content);
}