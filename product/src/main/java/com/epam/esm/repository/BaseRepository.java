package com.epam.esm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component

public abstract class BaseRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public BaseRepository(@Qualifier("createEntityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
