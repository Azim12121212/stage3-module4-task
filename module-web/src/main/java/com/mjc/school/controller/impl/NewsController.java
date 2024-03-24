package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsControllerInterface;
import com.mjc.school.service.NewsServiceInterface;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/getall")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public NewsDtoResponse create(@RequestBody NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @PutMapping(value = "/update/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        if (updateRequest.getId()>0 && updateRequest.getId()!=id) {
            throw new NotFoundException("Resource id in path and request body does not match.");
        }
        updateRequest.setId(id);
        return newsService.update(updateRequest);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return newsService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> readAll(@RequestParam(required = false, name = "page", defaultValue = "0") Integer pageNum,
                                         @RequestParam(required = false, name = "size", defaultValue = "5") Integer pageSize,
                                         @RequestParam(required = false, name = "sort", defaultValue = "title") String sortBy) {
        return newsService.readAll(pageNum, pageSize, sortBy);
    }

    // Get Author by news id – return author by provided news id.
    @GetMapping(value = "/{id:\\d+}/author")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorDtoResponse getAuthorByNewsId(@PathVariable("id") Long newsId) {
        return newsService.getAuthorByNewsId(newsId);
    }

    // Get Tags by news id – return tags by provided news id.
    @GetMapping(value = "/{id:\\d+}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Set<TagDtoResponse> getTagsByNewsId(@PathVariable("id") Long newsId) {
        return newsService.getTagsByNewsId(newsId);
    }

    // Get Comments by news id – return comments by provided news id.
    @GetMapping(value = "/{id:\\d+}/comments")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<CommentDtoResponse> getCommentsByNewsId(@PathVariable("id") Long newsId) {
        return null;
    }

    // Get News by title
    @GetMapping(value = "/{title}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> getNewsByTitle(@PathVariable String title) {
        return newsService.getNewsByTitle(title);
    }

    // Get News by content
    @GetMapping(value = "/{content}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<NewsDtoResponse> getNewsByContent(@PathVariable String content) {
        return newsService.getNewsByContent(content);
    }
}