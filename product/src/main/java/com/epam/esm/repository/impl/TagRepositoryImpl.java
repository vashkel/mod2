package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.TagRepository;
import exception.RepositoryException;
import exception.TagNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepositoryImpl extends BaseRepository implements TagRepository {

    private SimpleJdbcInsert tagInserter;

    private final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id=?";
    private final String SQL_FIND_TAG = "SELECT id, name FROM tag WHERE id=?";
    private final String SQL_FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
    private final String SQL_FIND_ALL_TAGS = "SELECT id, name FROM tag";

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.tagInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tag").usingColumns("name").usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(Tag tag) throws RepositoryException {
        try {
            Tag isCreated = findByName(tag.getName());
            if (isCreated != null) {
                return isCreated.getId();
            }
            Map<String, Object> values = new HashMap<>();
            values.put("name", tag.getName());
            return tagInserter.executeAndReturnKey(values).longValue();
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while create tag");
        }
    }

    @Override
    public boolean delete(Long tagId) throws RepositoryException {
        try {
            return getJdbcTemplate().update(SQL_DELETE_TAG, tagId) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while delete tag");
        }
    }

    @Override
    public Tag find(Long id) throws RepositoryException {
        try {
            return getJdbcTemplate().queryForObject(SQL_FIND_TAG, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find tag by id");
        }

    }

    @Override
    public Tag findByName(String tagName) throws RepositoryException {
        try {
            return getJdbcTemplate().queryForObject(SQL_FIND_TAG_BY_NAME, new TagMapper(), tagName);
        }catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find tag by name");
        }
    }

    @Override
    public List<Tag> findAll() throws RepositoryException {
        try {
            return getJdbcTemplate().query(SQL_FIND_ALL_TAGS, new TagMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find all tags");
        }
    }


}
