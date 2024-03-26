package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;

import java.util.List;
import java.util.Set;

public interface NewsRepositoryInterface extends BaseRepository<NewsModel, Long> {

    NewsModel partialUpdate(Long id, NewsModel entity);

    List<NewsModel> readAll(Integer pageNum, Integer pageSize, String sortBy);

    AuthorModel getAuthorByNewsId(Long newsId);

    Set<TagModel> getTagsByNewsId(Long newsId);

    List<CommentModel> getCommentsByNewsId(Long newsId);

    List<NewsModel> getNewsByTitle(String title);

    List<NewsModel> getNewsByContent(String content);
}