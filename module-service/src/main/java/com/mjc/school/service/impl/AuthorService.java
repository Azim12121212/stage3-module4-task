package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.AuthorServiceInterface;
import com.mjc.school.service.annotation.ValidatingAuthor;
import com.mjc.school.service.annotation.ValidatingAuthorId;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.errorsexceptions.Errors;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService implements AuthorServiceInterface {
    private final AuthorRepositoryInterface authorRepository;
    private final AuthorMapper mapper;
    private final NewsMapper newsMapper;

    @Autowired
    public AuthorService(AuthorRepositoryInterface authorRepository, AuthorMapper mapper, NewsMapper newsMapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.newsMapper = newsMapper;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return mapper.authorModelListToAuthorDtoList(authorRepository.readAll());
    }

    @ValidatingAuthorId
    @Override
    public AuthorDtoResponse readById(Long id) {
        return authorRepository.readById(id)
                .map(mapper::authorModelToAuthorDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_AUTHOR_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingAuthor
    @Transactional
    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = authorRepository.create(mapper.authorDtoToAuthorModel(createRequest));
        return mapper.authorModelToAuthorDto(authorModel);
    }

    @ValidatingAuthor
    @Transactional
    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if (readById(updateRequest.getId())!=null) {
            AuthorModel authorModel = authorRepository.update(mapper.authorDtoToAuthorModel(updateRequest));
            return mapper.authorModelToAuthorDto(authorModel);
        }
        return null;
    }

    @ValidatingAuthorId
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return authorRepository.deleteById(id);
        } else {
            return false;
        }
    }

    @Override
    public List<AuthorDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        return mapper.authorModelListToAuthorDtoList(authorRepository.readAll(pageNum, pageSize, sortBy));
    }

    // Get News by author name
    @Override
    public List<NewsDtoResponse> getNewsByAuthorName(String name) {
        try {
            List<NewsModel> newsModelList = authorRepository.getNewsByAuthorName(name);
            return newsMapper.newsModelListToNewsDtoList(newsModelList);
        } catch (Exception e) {
            Errors.ERROR_AUTHOR_NAME_NOT_EXIST.getErrorData(name, true);
        }
        return null;
    }
}