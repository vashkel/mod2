package com.epam.esm.repository;

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
    EntityManager entityManager;

    public BaseRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = getJdbcTemplate();
    }

    @Autowired
    public BaseRepository(@Qualifier("createEntityManager") EntityManager entityManager){
        this.entityManager = entityManager;
    }

//    protected BasicEntity persistWithTransaction(BasicEntity basicEntity){
//        entityManager.getTransaction().begin();
//        entityManager.persist(basicEntity);
//        entityManager.getTransaction().commit();
//        return basicEntity;
//    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    protected EntityManager getEntityManager(){
        return entityManager;
    }
}
