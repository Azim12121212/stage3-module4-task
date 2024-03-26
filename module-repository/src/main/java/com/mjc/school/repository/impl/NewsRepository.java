package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class NewsRepository implements NewsRepositoryInterface {
    private final DateTimeFormatter MY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @PersistenceContext
    private EntityManager entityManager;

    public NewsRepository() {
    }

    @Override
    public List<NewsModel> readAll() {
        return entityManager.createQuery("SELECT n FROM NewsModel n", NewsModel.class).getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(NewsModel.class, id));
    }

    @Override
    public NewsModel create(NewsModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setCreateDate(dateTime);
        entity.setLastUpdateDate(dateTime);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        NewsModel newsModel = null;
        if (existById(entity.getId())) {
            newsModel = entityManager.find(NewsModel.class, entity.getId());
            newsModel.setTitle(entity.getTitle());
            newsModel.setContent(entity.getContent());
            newsModel.setLastUpdateDate(dateTime);
            newsModel.setAuthorModel(entity.getAuthorModel());
            newsModel.setTagModelSet(entity.getTagModelSet());

            entityManager.merge(newsModel);
        }
        return newsModel;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(NewsModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(NewsModel.class, id)!=null;
    }

    @Override
    public NewsModel partialUpdate(Long id, NewsModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setLastUpdateDate(dateTime);
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public List<NewsModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        if (sortBy.equals("authorId")) {
            sortBy="authorModel";
        }

        String jpql = "SELECT n FROM NewsModel n ORDER BY n." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    // Get Author by news id – return author by provided news id.
    @Override
    public AuthorModel getAuthorByNewsId(Long newsId) {
        NewsModel newsModel = readById(newsId).get();
        return newsModel.getAuthorModel();
    }

    // Get Tags by news id – return tags by provided news id.
    @Override
    public Set<TagModel> getTagsByNewsId(Long newsId) {
        NewsModel newsModel = readById(newsId).get();
        return newsModel.getTagModelSet();
    }

    // Get Comments by news id – return comments by provided news id.
    @Override
    public List<CommentModel> getCommentsByNewsId(Long newsId) {
        NewsModel newsModel = readById(newsId).get();
        return newsModel.getCommentModelList();
    }

    // Get News by title
    @Override
    public List<NewsModel> getNewsByTitle(String title) {
        Query query = entityManager.createQuery("SELECT n FROM NewsModel n WHERE n.title LIKE :title")
                .setParameter("title", title);
        return query.getResultList();
    }

    // Get News by content
    @Override
    public List<NewsModel> getNewsByContent(String content) {
        Query query = entityManager.createQuery("SELECT n FROM NewsModel n WHERE n.content LIKE :content")
                .setParameter("content", "%" + content + "%");
        return query.getResultList();
    }
}