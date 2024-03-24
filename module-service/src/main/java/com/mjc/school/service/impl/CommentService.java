package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepositoryInterface;
import com.mjc.school.repository.model.CommentModel;
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

@Service
public class CommentService implements CommentServiceInterface {
    private final CommentRepositoryInterface commentRepository;
    private final CommentMapper mapper;

    @Autowired
    public CommentService(CommentRepositoryInterface commentRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
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
        CommentModel commentModel = commentRepository.create(mapper.commentDtoToCommentModel(createRequest));
        return mapper.commentModelToCommentDto(commentModel);
    }

    @ValidatingComment
    @Transactional
    @Override
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        if (readById(updateRequest.getId())!=null) {
            CommentModel commentModel = commentRepository.update(mapper.commentDtoToCommentModel(updateRequest));
            return mapper.commentModelToCommentDto(commentModel);
        }
        return null;
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
        return mapper.commentModelListToCommentDtoList(commentRepository.readAll(pageNum, pageSize, sortBy));
    }
}