package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepositoryInterface;
import com.mjc.school.repository.NewsRepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.NewsServiceInterface;
import com.mjc.school.service.annotation.ValidatingNews;
import com.mjc.school.service.annotation.ValidatingNewsId;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.errorsexceptions.Errors;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.CommentMapper;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class NewsService implements NewsServiceInterface {
    private final NewsRepositoryInterface newsRepository;
    private final AuthorRepositoryInterface authorRepository;
    private final NewsMapper mapper;
    private final AuthorMapper authorMapper;
    private final TagMapper tagMapper;
    private final CommentMapper commentMapper;

    @Autowired
    public NewsService(NewsRepositoryInterface newsRepository,
                       AuthorRepositoryInterface authorRepository,
                       NewsMapper mapper, AuthorMapper authorMapper,
                       TagMapper tagMapper, CommentMapper commentMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.authorMapper = authorMapper;
        this.tagMapper = tagMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return mapper.newsModelListToNewsDtoList(newsRepository.readAll());
    }

    @ValidatingNewsId
    @Override
    public NewsDtoResponse readById(Long id) {
        return newsRepository.readById(id)
                .map(mapper::newsModelToNewsDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_NEWS_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingNews
    @Transactional
    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        boolean equal = false;
        for (AuthorModel authorModel: authorRepository.readAll()) {
            if (Objects.equals(createRequest.getAuthorId(), authorModel.getId())) {
                equal = true;
            }
        }
        if (equal) {
            NewsModel newsModel = newsRepository.create(mapper.newsDtoToNewsModel(createRequest));
            return mapper.newsModelToNewsDto(newsModel);
        }
        return null;
    }

    @ValidatingNews
    @Transactional
    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        boolean equal = false;
        for (AuthorModel authorModel: authorRepository.readAll()) {
            if (Objects.equals(updateRequest.getAuthorId(), authorModel.getId())) {
                equal = true;
            }
        }
        if (equal && readById(updateRequest.getId())!=null) {
            NewsModel newsModel = newsRepository.update(mapper.newsDtoToNewsModel(updateRequest));
            return mapper.newsModelToNewsDto(newsModel);
        }
        return null;
    }

    @ValidatingNewsId
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return newsRepository.deleteById(id);
        } else {
            return false;
        }
    }

    @Override
    public List<NewsDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        return mapper.newsModelListToNewsDtoList(newsRepository.readAll(pageNum, pageSize, sortBy));
    }

    // Get Author by news id – return author by provided news id.
    @ValidatingNewsId
    @Override
    public AuthorDtoResponse getAuthorByNewsId(Long newsId) {
        if (readById(newsId)!=null) {
            AuthorModel authorModel = newsRepository.getAuthorByNewsId(newsId);
            return authorMapper.authorModelToAuthorDto(authorModel);
        }
        return null;
    }

    // Get Tags by news id – return tags by provided news id.
    @ValidatingNewsId
    @Override
    public Set<TagDtoResponse> getTagsByNewsId(Long newsId) {
        if (readById(newsId)!=null) {
            Set<TagModel> tagModelSet = newsRepository.getTagsByNewsId(newsId);
            return tagMapper.tagModelSetToTagDtoSet(tagModelSet);
        }
        return null;
    }

    // Get Comments by news id – return comments by provided news id.
    @ValidatingNewsId
    @Override
    public List<CommentDtoResponse> getCommentsByNewsId(Long newsId) {
        if (readById(newsId)!=null) {
            List<CommentModel> commentModelList = newsRepository.getCommentsByNewsId(newsId);
            return commentMapper.commentModelListToCommentDtoList(commentModelList);
        }
        return null;
    }

    // Get News by title
    @Override
    public List<NewsDtoResponse> getNewsByTitle(String title) {
        List<NewsModel> newsModelList = newsRepository.getNewsByTitle(title);
        return mapper.newsModelListToNewsDtoList(newsModelList);
    }

    // Get News by content
    @Override
    public List<NewsDtoResponse> getNewsByContent(String content) {
        List<NewsModel> newsModelList = newsRepository.getNewsByContent(content);
        return mapper.newsModelListToNewsDtoList(newsModelList);
    }
}