package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepositoryInterface;
import com.mjc.school.repository.NewsRepositoryInterface;
import com.mjc.school.repository.TagRepositoryInterface;
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
import com.mjc.school.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NewsService implements NewsServiceInterface {
    private final NewsRepositoryInterface newsRepository;
    private final AuthorRepositoryInterface authorRepository;
    private final TagRepositoryInterface tagRepository;
    private final NewsMapper mapper;
    private final AuthorMapper authorMapper;
    private final TagMapper tagMapper;
    private final CommentMapper commentMapper;
    private final Validator validator;

    @Autowired
    public NewsService(NewsRepositoryInterface newsRepository,
                       AuthorRepositoryInterface authorRepository,
                       TagRepositoryInterface tagRepository,
                       NewsMapper mapper, AuthorMapper authorMapper,
                       TagMapper tagMapper, CommentMapper commentMapper,
                       Validator validator) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.authorMapper = authorMapper;
        this.tagMapper = tagMapper;
        this.commentMapper = commentMapper;
        this.validator = validator;
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

    @Transactional
    @Override
    public NewsDtoResponse partialUpdate(Long id, NewsDtoRequest newsDtoRequest) {
        validator.validateNewsId(id);
        Optional<NewsModel> existingNewsModel = newsRepository.readById(id);

        if (existingNewsModel.isPresent()) {
            if (newsDtoRequest.getTitle()!=null) {
                validator.validateNewsTitle(newsDtoRequest.getTitle());
                existingNewsModel.get().setTitle(newsDtoRequest.getTitle());
            }
            if (newsDtoRequest.getContent()!=null) {
                validator.validateNewsContent(newsDtoRequest.getContent());
                existingNewsModel.get().setContent(newsDtoRequest.getContent());
            }
            if (newsDtoRequest.getAuthorId()!=null) {
                validator.validateAuthorId(newsDtoRequest.getAuthorId());
                if (authorRepository.existById(newsDtoRequest.getAuthorId())) {
                    existingNewsModel.get().setAuthorModel(authorMapper.authorIdToAuthorModel(newsDtoRequest.getAuthorId()));
                } else {
                    throw new NotFoundException(Errors.ERROR_AUTHOR_ID_NOT_EXIST.getErrorData(String.valueOf(newsDtoRequest.getAuthorId()), true));
                }
            }
            if (newsDtoRequest.getTagIdList()!=null) {
                for (Long tagId: newsDtoRequest.getTagIdList()) {
                    validator.validateTagId(tagId);
                    if (tagRepository.existById(tagId)) {
                        TagModel tagModel = tagRepository.readById(tagId).get();
                        existingNewsModel.get().getTagModelSet().add(tagModel);
                    } else {
                        throw new NotFoundException(Errors.ERROR_TAG_ID_NOT_EXIST.getErrorData(String.valueOf(tagId), true));
                    }
                }
            }
            NewsModel newsModel = newsRepository.partialUpdate(id, existingNewsModel.get());
            return mapper.newsModelToNewsDto(newsModel);
        } else {
            throw new NotFoundException(Errors.ERROR_NEWS_ID_NOT_EXIST.getErrorData(String.valueOf(id), true));
        }
    }

    @Override
    public List<NewsDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        if (sortBy.equals("id") || sortBy.equals("title") ||
                sortBy.equals("content") || sortBy.equals("authorId")) {
            return mapper.newsModelListToNewsDtoList(newsRepository.readAll(pageNum, pageSize, sortBy));
        } else {
            throw new NotFoundException("sort param value '" + sortBy + "' is incorrect!");
        }
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
            if (tagModelSet!=null && !tagModelSet.isEmpty()) {
                return tagMapper.tagModelSetToTagDtoSet(tagModelSet);
            } else {
                throw new NotFoundException(Errors.ERROR_TAG_NAME_NOT_EXIST.getErrorData(String.valueOf(newsId), true));
            }
        }
        return null;
    }

    // Get Comments by news id – return comments by provided news id.
    @ValidatingNewsId
    @Override
    public List<CommentDtoResponse> getCommentsByNewsId(Long newsId) {
        if (readById(newsId)!=null) {
            List<CommentModel> commentModelList = newsRepository.getCommentsByNewsId(newsId);
            if (commentModelList!=null && !commentModelList.isEmpty()) {
                return commentMapper.commentModelListToCommentDtoList(commentModelList);
            } else {
                throw new NotFoundException(Errors.ERROR_COMMENT_BY_NEWS_ID.getErrorData(String.valueOf(newsId), true));
            }
        }
        return null;
    }

    // Get News by title
    @Override
    public List<NewsDtoResponse> getNewsByTitle(String title) {
        List<NewsModel> newsModelList = newsRepository.getNewsByTitle(title);
        if (newsModelList!=null && !newsModelList.isEmpty()) {
            return mapper.newsModelListToNewsDtoList(newsModelList);
        } else {
            throw new NotFoundException(Errors.ERROR_NEWS_TITLE_NOT_EXIST.getErrorData(title, true));
        }
    }

    // Get News by content
    @Override
    public List<NewsDtoResponse> getNewsByContent(String content) {
        List<NewsModel> newsModelList = newsRepository.getNewsByContent(content);
        if (newsModelList!=null && !newsModelList.isEmpty()) {
            return mapper.newsModelListToNewsDtoList(newsModelList);
        } else {
            throw new NotFoundException(Errors.ERROR_NEWS_CONTENT_NOT_EXIST.getErrorData(content, true));
        }
    }
}