package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepositoryInterface;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements CommentRepositoryInterface {
    private final DateTimeFormatter MY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CommentModel> readAll() {
        return entityManager.createQuery("SELECT c FROM CommentModel c LEFT JOIN c.newsModel n", CommentModel.class).getResultList();
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(CommentModel.class, id));
    }

    @Override
    public CommentModel create(CommentModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setCreateDate(dateTime);
        entity.setLastUpdateDate(dateTime);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CommentModel update(CommentModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        CommentModel commentModel = null;
        if (existById(entity.getId())) {
            commentModel = entityManager.find(CommentModel.class, entity.getId());
            commentModel.setContent(entity.getContent());
            commentModel.setLastUpdateDate(dateTime);
            commentModel.setNewsModel(entity.getNewsModel());

            entityManager.merge(commentModel);
        }
        return commentModel;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(CommentModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.getReference(CommentModel.class, id)!=null;
    }

    @Override
    public List<CommentModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        if (sortBy.equals("newsId")) {
            sortBy="newsModel";
        }
        String jpql = "SELECT c FROM CommentModel c ORDER BY c." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }
}