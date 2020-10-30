package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import exception.RepositoryException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert tagInserter;

    private final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id=?";
    private final String SQL_FIND_TAG = "SELECT id, name FROM tag WHERE id=?";
    private final String SQL_FIND_ALL_TAGS = "SELECT id, name FROM tag";

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tag").usingColumns("name").usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(Tag tag) throws RepositoryException {
        Map<String,Object> values = new HashMap<>();
        values.put("name", tag.getName());
        try {
            return tagInserter.executeAndReturnKey(values).longValue();
        }catch (DataAccessException e){
            throw new RepositoryException("Exception while create tag");
        }
    }

    @Override
    public void delete(Long tagId) throws RepositoryException {
        try {
            jdbcTemplate.update(SQL_DELETE_TAG, tagId);
        }catch (DataAccessException e){
            throw new RepositoryException("Exception while delete tag");
        }
    }

    @Override
    public Tag find(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_TAG, new TagMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (DataAccessException e){
            throw new RepositoryException("Exception while find tag by id");
        }

    }

    @Override
    public List<Tag> findAll() throws RepositoryException {
       try {
           return jdbcTemplate.query(SQL_FIND_ALL_TAGS, new TagMapper());
       }catch (EmptyResultDataAccessException e){
           return null;
       }catch (DataAccessException e){
           throw new RepositoryException("Exception while find all tags");
       }
    }


}
