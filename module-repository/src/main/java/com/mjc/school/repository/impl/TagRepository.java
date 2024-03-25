package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepositoryInterface;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class TagRepository implements TagRepositoryInterface {
    @PersistenceContext
    private EntityManager entityManager;

    public TagRepository() {
    }

    @Override
    public List<TagModel> readAll() {
        return entityManager.createQuery("SELECT t FROM TagModel t", TagModel.class).getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
        TagModel tagModel = null;
        if (existById(entity.getId())) {
            tagModel = entityManager.find(TagModel.class, entity.getId());
            tagModel.setName(entity.getName());
            entityManager.merge(tagModel);
        }
        return tagModel;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(TagModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.getReference(TagModel.class, id)!=null;
    }

    @Override
    public List<TagModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        String jpql = "SELECT t FROM TagModel t ORDER BY t." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    // Get News by tag id
    @Override
    public List<NewsModel> getNewsByTagId(Long id) {
        String jpql = "SELECT n FROM NewsModel n JOIN FETCH n.tagModelSet t WHERE t.id = :tagId";

        List<NewsModel> newsModelList = entityManager.createQuery(jpql)
                .setParameter("tagId", id)
                .getResultList();

        return newsModelList;
    }

    // Get News by tag name
    @Override
    public List<NewsModel> getNewsByTagName(String name) {
        Query query = entityManager.createQuery("SELECT t FROM TagModel t WHERE t.name LIKE :name")
                .setParameter("name", "%" + name + "%");

        List<TagModel> tagModelList = query.getResultList();

        Set<NewsModel> newsModelSet = new HashSet<>();
        for (NewsModel newsModel: tagModelList.iterator().next().getNewsModelSet()) {
            newsModelSet.add(newsModel);
        }

        List<NewsModel> newsModelList = new ArrayList<>(newsModelSet);

        return newsModelList;
    }
}