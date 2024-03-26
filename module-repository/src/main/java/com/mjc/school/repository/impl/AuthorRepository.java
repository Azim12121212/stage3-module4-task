package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements AuthorRepositoryInterface {
    private final DateTimeFormatter MY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorModel> readAll() {
        return entityManager.createQuery("SELECT a FROM AuthorModel a",
                AuthorModel.class).getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setCreateDate(dateTime);
        entity.setLastUpdateDate(dateTime);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        AuthorModel authorModel = null;
        if (existById(entity.getId())) {
            authorModel = entityManager.find(AuthorModel.class, entity.getId());
            authorModel.setName(entity.getName());
            authorModel.setLastUpdateDate(dateTime);
            entityManager.merge(authorModel);
        }
        return authorModel;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(AuthorModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(AuthorModel.class, id)!=null;
    }

    @Override
    public AuthorModel partialUpdate(Long id, AuthorModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setLastUpdateDate(dateTime);
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public List<AuthorModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        String jpql = "SELECT a FROM AuthorModel a ORDER BY a." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    // Get News by author name
    @Override
    public List<NewsModel> getNewsByAuthorName(String name) {
        AuthorModel authorModel = (AuthorModel) entityManager
                .createQuery("SELECT a FROM AuthorModel a WHERE a.name = :name")
                .setParameter("name", name).getSingleResult();

        return authorModel.getNewsModelList();
    }
}