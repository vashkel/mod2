package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends BaseRepository implements TagRepository {


    @Autowired
    public TagRepositoryImpl(@Qualifier("createEntityManager") EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Tag> create(Tag tag) {
        getEntityManager().persist(tag);
        return Optional.ofNullable(tag);
    }

    @Override
    public void delete(Tag tag) {
        getEntityManager().remove(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(getEntityManager().createNamedQuery("Tag.findById", Tag.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        try {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findByName", Tag.class)
                    .setParameter("name", tagName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Tag>> findAll() {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findAll", Tag.class)
                    .getResultList());
    }

    @Override
    public Optional<List<Tag>> findAllTagsByCertificateId(Long id) {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findAllTagsByCertificateId", Tag.class)
                    .setParameter("id", id)
                    .getResultList());
    }
}
