package com.mjc.school.service.impl;

import com.mjc.school.repository.TagRepositoryInterface;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagServiceInterface;
import com.mjc.school.service.annotation.ValidatingTag;
import com.mjc.school.service.annotation.ValidatingTagId;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.errorsexceptions.Errors;
import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements TagServiceInterface {
    private final TagRepositoryInterface tagRepository;
    private final TagMapper mapper;
    private final NewsMapper newsMapper;
    private final Validator validator;

    @Autowired
    public TagService(TagRepositoryInterface tagRepository, TagMapper mapper,
                      NewsMapper newsMapper, Validator validator) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.newsMapper = newsMapper;
        this.validator = validator;
    }

    @Override
    public List<TagDtoResponse> readAll() {
        return mapper.tagModelListToTagDtoList(tagRepository.readAll());
    }

    @ValidatingTagId
    @Override
    public TagDtoResponse readById(Long id) {
        return tagRepository.readById(id)
                .map(mapper::tagModelToTagDto)
                .orElseThrow(() -> new NotFoundException(Errors.ERROR_TAG_ID_NOT_EXIST.getErrorData(String.valueOf(id), true)));
    }

    @ValidatingTag
    @Transactional
    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        TagModel tagModel = tagRepository.create(mapper.tagDtoToTagModel(createRequest));
        return mapper.tagModelToTagDto(tagModel);
    }

    @ValidatingTag
    @Transactional
    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        if (readById(updateRequest.getId())!=null) {
            TagModel tagModel = tagRepository.update(mapper.tagDtoToTagModel(updateRequest));
            return mapper.tagModelToTagDto(tagModel);
        }
        return null;
    }

    @ValidatingTagId
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (readById(id)!=null) {
            return tagRepository.deleteById(id);
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public TagDtoResponse partialUpdate(Long id, TagDtoRequest updateRequest) {
        validator.validateTagId(id);
        Optional<TagModel> existingTagModel = tagRepository.readById(id);

        if (existingTagModel.isPresent()) {
            if (updateRequest.getName()!=null) {
                validator.validateTagName(updateRequest.getName());
                existingTagModel.get().setName(updateRequest.getName());
            }
            TagModel tagModel = tagRepository.partialUpdate(id, existingTagModel.get());
            return mapper.tagModelToTagDto(tagModel);
        } else {
            throw new NotFoundException(Errors.ERROR_TAG_ID_NOT_EXIST.getErrorData(String.valueOf(id), true));
        }
    }

    @Override
    public List<TagDtoResponse> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        if (sortBy.equals("id") || sortBy.equals("name")) {
            return mapper.tagModelListToTagDtoList(tagRepository.readAll(pageNum, pageSize, sortBy));
        } else {
            throw new NotFoundException("sort param value '" + sortBy + "' is incorrect!");
        }
    }

    // Get News by tag id
    @ValidatingTagId
    @Override
    public List<NewsDtoResponse> getNewsByTagId(Long id) {
        if (readById(id)!=null) {
            List<NewsModel> newsModelList = tagRepository.getNewsByTagId(id);
            return newsMapper.newsModelListToNewsDtoList(newsModelList);
        }
        return null;
    }

    // Get News by tag name
    @Override
    public List<NewsDtoResponse> getNewsByTagName(String name) {
        List<NewsModel> newsModelList = tagRepository.getNewsByTagName(name);
        if (newsModelList!=null && !newsModelList.isEmpty()) {
            return newsMapper.newsModelListToNewsDtoList(newsModelList);
        } else {
            throw new NotFoundException(Errors.ERROR_TAG_NAME_NOT_EXIST.getErrorData(name, true));
        }
    }
}