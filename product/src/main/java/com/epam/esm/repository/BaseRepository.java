package com.epam.esm.repository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component

public abstract class BaseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = getJdbcTemplate();
    }



    private EntityManager entityManager;

    @Autowired
    public BaseRepository(@Qualifier("createEntityManager") EntityManager entityManager){
        this.entityManager = entityManager;
    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected EntityManager entityManager() {
        return entityManager;
    }
}
