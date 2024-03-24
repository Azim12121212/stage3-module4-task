package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;

public interface AuthorRepositoryInterface extends BaseRepository<AuthorModel, Long> {

    List<AuthorModel> readAll(Integer pageNum, Integer pageSize, String sortBy);

    List<NewsModel> getNewsByAuthorName(String name);
}