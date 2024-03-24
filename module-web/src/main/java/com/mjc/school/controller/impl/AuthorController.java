package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorControllerInterface;
import com.mjc.school.service.AuthorServiceInterface;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/getall")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorService.readAll();
    }

    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorDtoResponse readById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public AuthorDtoResponse create(@RequestBody AuthorDtoRequest createRequest) {
        return authorService.create(createRequest);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorDtoResponse update(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        if (updateRequest.getId()>0 && updateRequest.getId()!=id) {
            throw new NotFoundException("Resource id in path and request body does not match.");
        }
        updateRequest.setId(id);
        return authorService.update(updateRequest);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return authorService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<AuthorDtoResponse> readAll(@RequestParam(required = false, name = "page", defaultValue = "0") Integer pageNum,
                                           @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                           @RequestParam(required = false, name = "sort", defaultValue = "name") String sortBy) {
        return authorService.readAll(pageNum, pageSize, sortBy);
    }

    // Get News by author name
    @GetMapping(value = "/{name}/news")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> getNewsByAuthorName(@PathVariable String name) {
        return authorService.getNewsByAuthorName(name);
    }
}