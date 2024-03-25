package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepositoryInterface;
import com.mjc.school.repository.NewsRepositoryInterface;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.CommentServiceInterface;
import com.mjc.school.service.annotation.ValidatingComment;
import com.mjc.school.service.annotation.ValidatingCommentId;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.errorsexceptions.Errors;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CommentService implements CommentServiceInterface {
    private final CommentRepositoryInterface commentRepository;
    private final NewsRepositoryInterface newsRepository;
    private final CommentMapper mapper;

    @Autowired
    public CommentService(CommentRepositoryInterface commentRepository,
                          NewsRepositoryInterface newsRepository,
                          CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDtoResponse> readAll() {
        return mapper.commentModelListToCommentDtoList(commentRepository.readAll());
    }

    @ValidatingCommentId
    @Override
    public CommentDtoResponse readById(Long id) {
        return commentRepository.readById(id)
                .map(mapper::commentModelToCommentDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_COMMENT_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingComment
    @Transactional
    @Override
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        boolean equal = false;
        for (NewsModel newsModel: newsRepository.readAll()) {
            if (Objects.equals(createRequest.getNewsId(), newsModel.getId())) {
                equal = true;
            }
        }
        if (equal) {
            CommentModel commentModel = commentRepository.create(mapper.commentDtoToCommentModel(createRequest));
            return mapper.commentModelToCommentDto(commentModel);
        } else {
            throw new NotFoundException(Errors.ERROR_NEWS_ID_NOT_EXIST.getErrorData(String.valueOf(createRequest.getNewsId()), true));
        }
    }

    @ValidatingComment
    @Transactional
    @Override
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        boolean equal = false;
        for (NewsModel newsModel: newsRepository.readAll()) {
            if (Objects.equals(updateRequest.getNewsId(), newsModel.getId())) {
                equal = true;
            }
        }
        if (equal && readById(updateRequest.getId())!=null) {
            CommentModel commentModel = commentRepository.update(mapper.commentDtoToCommentModel(updateRequest));
            return mapper.commentModelToCommentDto(commentModel);
        } else {
            throw new NotFoundException(Errors.ERROR_NEWS_ID_NOT_EXIST.getErrorData(String.valueOf(updateRequest.getNewsId()), true));
        }
    }

    @ValidatingCommentId
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return commentRepository.deleteById(id);
        } else {
            return false;
        }
    }

    @Override
    public List<CommentDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        if (sortBy.equals("id") || sortBy.equals("content") || sortBy.equals("newsId")) {
            return mapper.commentModelListToCommentDtoList(commentRepository.readAll(pageNum, pageSize, sortBy));
        } else {
            throw new NotFoundException("sort param value '" + sortBy + "' is incorrect!");
        }
    }
}