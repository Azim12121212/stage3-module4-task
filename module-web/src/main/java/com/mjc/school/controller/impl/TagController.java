package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagControllerInterface;
import com.mjc.school.service.TagServiceInterface;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping(value = "/getall")
//    @Override
//    public ResponseEntity<List<TagDtoResponse>> readAll() {
//        List<TagDtoResponse> tagDtoResponseList = tagService.readAll();
//        return new ResponseEntity<>(tagDtoResponseList, HttpStatus.OK);
//    }

    @GetMapping(value = "/{id:\\d+}")
    @Override
    public ResponseEntity<TagDtoResponse> readById(@PathVariable Long id) {
        TagDtoResponse tagDtoResponse = tagService.readById(id);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<TagDtoResponse> create(@RequestBody TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = tagService.create(createRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @Override
    public ResponseEntity<TagDtoResponse> update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        updateRequest.setId(id);
        TagDtoResponse tagDtoResponse = tagService.update(updateRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    @PatchMapping(value = "/partial-update/{id:\\d+}")
    @Override
    public ResponseEntity<TagDtoResponse> partialUpdate(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        TagDtoResponse tagDtoResponse = tagService.partialUpdate(id, updateRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<TagDtoResponse>> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                        @RequestParam(required = false, name = "sort", defaultValue = "name") String sortBy) {
        List<TagDtoResponse> tagDtoResponseList = tagService.readAll(pageNum, pageSize, sortBy);
        return new ResponseEntity<>(tagDtoResponseList, HttpStatus.OK);
    }

    // Get News by tag id
    @GetMapping(value = "/byid/{id:\\d+}/news")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> getNewsByTagId(@PathVariable Long id) {
        List<NewsDtoResponse> newsDtoResponseList = tagService.getNewsByTagId(id);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }

    // Get News by tag name
    @GetMapping(value = "/byname/{name}/news")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> getNewsByTagName(@PathVariable String name) {
        List<NewsDtoResponse> newsDtoResponseList = tagService.getNewsByTagName(name);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }
}