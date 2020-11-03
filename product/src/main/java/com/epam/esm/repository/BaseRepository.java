package com.epam.esm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;



}
