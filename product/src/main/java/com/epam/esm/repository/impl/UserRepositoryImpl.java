package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl  extends BaseRepository implements UserRepository {

    public UserRepositoryImpl(@Qualifier("createEntityManager")EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(getEntityManager()
                    .createQuery("FROM User WHERE id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<User>> findAll(int offset, int limit) {
        return Optional.ofNullable(getEntityManager()
                .createNamedQuery("User.findAll", User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }
}
