package com.epam.esm.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = getJdbcTemplate();
    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
