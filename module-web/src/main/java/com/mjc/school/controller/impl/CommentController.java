package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentControllerInterface;
import com.mjc.school.service.CommentServiceInterface;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping(value = "/getall")
//    @Override
//    public ResponseEntity<List<CommentDtoResponse>> readAll() {
//        List<CommentDtoResponse> commentDtoResponseList = commentService.readAll();
//        return new ResponseEntity<>(commentDtoResponseList, HttpStatus.OK);
//    }

    @GetMapping(value = "/{id:\\d+}")
    @Override
    public ResponseEntity<CommentDtoResponse> readById(@PathVariable Long id) {
        CommentDtoResponse commentDtoResponse = commentService.readById(id);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<CommentDtoResponse> create(@RequestBody CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = commentService.create(createRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @Override
    public ResponseEntity<CommentDtoResponse> update(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        updateRequest.setId(id);
        CommentDtoResponse commentDtoResponse = commentService.update(updateRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @PatchMapping(value = "/partial-update/{id:\\d+}")
    @Override
    public ResponseEntity<CommentDtoResponse> partialUpdate(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        CommentDtoResponse commentDtoResponse = commentService.partialUpdate(id, updateRequest);
        return new ResponseEntity<>(commentDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<CommentDtoResponse>> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortBy) {
        List<CommentDtoResponse> commentDtoResponseList = commentService.readAll(pageNum, pageSize, sortBy);
        return new ResponseEntity<>(commentDtoResponseList, HttpStatus.OK);
    }
}