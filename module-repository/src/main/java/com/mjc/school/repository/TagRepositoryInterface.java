package com.mjc.school.repository;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;

import java.util.List;

public interface TagRepositoryInterface extends BaseRepository<TagModel, Long> {

    List<TagModel> readAll(Integer pageNum, Integer pageSize, String sortBy);

    List<NewsModel> getNewsByTagId(Long id);

    List<NewsModel> getNewsByTagName(String name);
}
