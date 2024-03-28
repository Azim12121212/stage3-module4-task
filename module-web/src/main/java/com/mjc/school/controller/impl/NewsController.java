package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsControllerInterface;
import com.mjc.school.service.NewsServiceInterface;
import com.mjc.school.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/news-app/v1.0/news")
public class NewsController implements NewsControllerInterface {
    private final NewsServiceInterface newsService;

    @Autowired
    public NewsController(NewsServiceInterface newsService) {
        this.newsService = newsService;
    }

//    @GetMapping(value = "/getall")
//    @Override
//    public ResponseEntity<List<NewsDtoResponse>> readAll() {
//        List<NewsDtoResponse> newsDtoResponseList = newsService.readAll();
//        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
//    }

    @GetMapping(value = "/{id:\\d+}")
    @Override
    public ResponseEntity<NewsDtoResponse> readById(@PathVariable Long id) {
        NewsDtoResponse newsDtoResponse = newsService.readById(id);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<NewsDtoResponse> create(@RequestBody NewsDtoRequest createRequest) {
        NewsDtoResponse newsDtoResponse = newsService.create(createRequest);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @Override
    public ResponseEntity<NewsDtoResponse> update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        updateRequest.setId(id);
        NewsDtoResponse newsDtoResponse = newsService.update(updateRequest);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @PatchMapping(value = "/partial-update/{id:\\d+}")
    @Override
    public ResponseEntity<NewsDtoResponse> partialUpdate(@PathVariable Long id, @RequestBody NewsDtoRequest newsDtoRequest) {
        NewsDtoResponse newsDtoResponse = newsService.partialUpdate(id, newsDtoRequest);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/pages")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> readAll(@RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                         @RequestParam(required = false, name = "sort", defaultValue = "title") String sortBy) {
        List<NewsDtoResponse> newsDtoResponseList = newsService.readAll(pageNum, pageSize, sortBy);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }

    // Get Author by news id – return author by provided news id.
    @GetMapping(value = "/{id:\\d+}/author")
    @Override
    public ResponseEntity<AuthorDtoResponse> getAuthorByNewsId(@PathVariable("id") Long newsId) {
        AuthorDtoResponse authorDtoResponse = newsService.getAuthorByNewsId(newsId);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    // Get Tags by news id – return tags by provided news id.
    @GetMapping(value = "/{id:\\d+}/tags")
    @Override
    public ResponseEntity<Set<TagDtoResponse>> getTagsByNewsId(@PathVariable("id") Long newsId) {
        Set<TagDtoResponse> tagDtoResponseSet = newsService.getTagsByNewsId(newsId);
        return new ResponseEntity<>(tagDtoResponseSet, HttpStatus.OK);
    }

    // Get Comments by news id – return comments by provided news id.
    @GetMapping(value = "/{id:\\d+}/comments")
    @Override
    public ResponseEntity<List<CommentDtoResponse>> getCommentsByNewsId(@PathVariable("id") Long newsId) {
        List<CommentDtoResponse> commentDtoResponseList = newsService.getCommentsByNewsId(newsId);
        return new ResponseEntity<>(commentDtoResponseList, HttpStatus.OK);
    }

    // Get News by title
    @GetMapping(value = "/title/{title}")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> getNewsByTitle(@PathVariable String title) {
        List<NewsDtoResponse> newsDtoResponseList = newsService.getNewsByTitle(title);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }

    // Get News by content
    @GetMapping(value = "/content/{content}")
    @Override
    public ResponseEntity<List<NewsDtoResponse>> getNewsByContent(@PathVariable String content) {
        List<NewsDtoResponse> newsDtoResponseList = newsService.getNewsByContent(content);
        return new ResponseEntity<>(newsDtoResponseList, HttpStatus.OK);
    }
}