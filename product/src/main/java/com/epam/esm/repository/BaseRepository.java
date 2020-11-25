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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("createEntityManager")
    private EntityManager entityManager;

    public BaseRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = getJdbcTemplate();
    }

    @Autowired
    public BaseRepository(@Qualifier("createEntityManager") EntityManager entityManager){
        this.entityManager = entityManager;
    }

    protected  <T> T persistWithTransaction(T entity){
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
        return (T) entity;
    }

    protected <T> void removeWithTransaction(T entity){
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(entity);
        getEntityManager().getTransaction().commit();
    }


    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    protected EntityManager getEntityManager(){
        return entityManager;
    }
}
