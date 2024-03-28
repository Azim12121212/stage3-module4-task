package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorControllerInterface;
import com.mjc.school.service.AuthorServiceInterface;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news-app/v1.0/authors")
public class AuthorController implements AuthorControllerInterface {
    private final AuthorServiceInterface authorService;

    @Autowired
    public AuthorController(AuthorServiceInterface authorService) {
        this.authorService = authorService;
    }

//    @GetMapping(value = "/getall")
//    @Override
//    public ResponseEntity<List<AuthorDtoResponse>> readAll() {
//        List<AuthorDtoResponse> authorDtoResponseList = authorService.readAll();
//        return new ResponseEntity<>(authorDtoResponseList, HttpStatus.OK);
//    }

    @GetMapping(value = "/{id:\\d+}")
    @Override
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable Long id) {
        AuthorDtoResponse authorDtoResponse = authorService.readById(id);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<AuthorDtoResponse> create(@RequestBody AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.create(createRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @Override
    public ResponseEntity<AuthorDtoResponse> update(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        updateRequest.setId(id);
        AuthorDtoResponse authorDtoResponse = authorService.update(updateRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    @PatchMapping(value = "/partial-update/{id:\\d+}")
    @Override
    public ResponseEntity<AuthorDtoResponse> partialUpdate(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.partialUpdate(id, updateRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<AuthorDtoResponse>> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                           @RequestParam(required = false, name = "sort", defaultValue = "name") String sortBy) {
        List<AuthorDtoResponse> authorDtoResponseList = authorService.readAll(pageNum, pageSize, sortBy);
        return new ResponseEntity<>(authorDtoResponseList, HttpStatus.OK);
    }

    // Get News by author name
    @GetMapping(value = "/{name}/news")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> getNewsByAuthorName(@PathVariable String name) {
        List<NewsDtoResponse> newsDtoResponseList = authorService.getNewsByAuthorName(name);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }
}