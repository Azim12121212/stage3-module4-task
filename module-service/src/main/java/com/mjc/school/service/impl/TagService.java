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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService implements TagServiceInterface {
    private final TagRepositoryInterface tagRepository;
    private final TagMapper mapper;
    private final NewsMapper newsMapper;

    @Autowired
    public TagService(TagRepositoryInterface tagRepository, TagMapper mapper, NewsMapper newsMapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.newsMapper = newsMapper;
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
        try {
            List<NewsModel> newsModelList = tagRepository.getNewsByTagName(name);
            return newsMapper.newsModelListToNewsDtoList(newsModelList);
        } catch (Exception e) {
            Errors.ERROR_TAG_NAME_NOT_EXIST.getErrorData(name, true);
        }
        return null;
    }
}