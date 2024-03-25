package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagControllerInterface;
import com.mjc.school.service.TagServiceInterface;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news-app/v1.0/tags")
public class TagController implements TagControllerInterface {
    private final TagServiceInterface tagService;

    @Autowired
    public TagController(TagServiceInterface tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/getall")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<TagDtoResponse> readAll() {
        return tagService.readAll();
    }

    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public TagDtoResponse readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public TagDtoResponse create(@RequestBody TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        updateRequest.setId(id);
        return tagService.update(updateRequest);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return tagService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<TagDtoResponse> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                        @RequestParam(required = false, name = "sort", defaultValue = "name") String sortBy) {
        return tagService.readAll(pageNum, pageSize, sortBy);
    }

    // Get News by tag id
    @GetMapping(value = "/byid/{id:\\d+}/news")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> getNewsByTagId(@PathVariable Long id) {
        return tagService.getNewsByTagId(id);
    }

    // Get News by tag name
    @GetMapping(value = "/byname/{name}/news")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> getNewsByTagName(@PathVariable String name) {
        return tagService.getNewsByTagName(name);
    }
}