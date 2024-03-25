package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentControllerInterface;
import com.mjc.school.service.CommentServiceInterface;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news-app/v1.0/comments")
public class CommentController implements CommentControllerInterface {
    private final CommentServiceInterface commentService;

    @Autowired
    public CommentController(CommentServiceInterface commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/getall")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<CommentDtoResponse> readAll() {
        return commentService.readAll();
    }

    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommentDtoResponse readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public CommentDtoResponse create(@RequestBody CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        updateRequest.setId(id);
        return commentService.update(updateRequest);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return commentService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<CommentDtoResponse> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortBy) {
        return commentService.readAll(pageNum, pageSize, sortBy);
    }
}