package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.util.query.TagConstantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:sql_query_tag.properties")
public class TagRepositoryImpl extends BaseRepository implements TagRepository {

    private SimpleJdbcInsert tagInserter;

    @Autowired
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
            return getJdbcTemplate().update(TagConstantQuery.SQL_DELETE_TAG, tagId) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while delete tag");
        }
    }

    @Override
    public Tag find(Long id) throws RepositoryException {
        try {
            return getJdbcTemplate().queryForObject(TagConstantQuery.SQL_FIND_TAG, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find tag by id");
        }
    }

    @Override
    public Tag findByName(String tagName) throws RepositoryException {
        try {
            return getJdbcTemplate().queryForObject(TagConstantQuery.SQL_FIND_TAG_BY_NAME, new TagMapper(), tagName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find tag by name");
        }
    }

    @Override
    public List<Tag> findAll() throws RepositoryException {
        try {
            return getJdbcTemplate().query(TagConstantQuery.SQL_FIND_ALL_TAGS, new TagMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find all tags");
        }
    }

    @Override
    public List<Tag> findAllTagsByCertificateId(Long id) throws RepositoryException {
        try {
            return getJdbcTemplate().query(TagConstantQuery.SQL_FIND_ALL_TAGS_BY_CERTIFICATE_ID, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find all tags");
        }
    }
}
