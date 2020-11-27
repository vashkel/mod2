package com.epam.esm.repository;

import com.epam.esm.entity.BasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component

public abstract class BaseRepository {

    @Autowired
    @Qualifier("createEntityManager")
    private EntityManager entityManager;

    @Autowired
    public BaseRepository(@Qualifier("createEntityManager") EntityManager entityManager){
        this.entityManager = entityManager;
    }
    protected EntityManager getEntityManager(){
        return entityManager;
    }
}
