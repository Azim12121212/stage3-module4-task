package com.mjc.school.controller;

import com.mjc.school.service.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface NewsControllerInterface extends BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    ResponseEntity<NewsDtoResponse> partialUpdate(Long id, NewsDtoRequest newsDtoRequest);

    ResponseEntity<List<NewsDtoResponse>> readAll(Integer pageNum, Integer pageSize, String sortBy);

    ResponseEntity<AuthorDtoResponse> getAuthorByNewsId(Long newsId);

    ResponseEntity<Set<TagDtoResponse>> getTagsByNewsId(Long newsId);

    ResponseEntity<List<CommentDtoResponse>> getCommentsByNewsId(Long newsId);

    ResponseEntity<List<NewsDtoResponse>> getNewsByTitle(String title);

    ResponseEntity<List<NewsDtoResponse>> getNewsByContent(String content);
}