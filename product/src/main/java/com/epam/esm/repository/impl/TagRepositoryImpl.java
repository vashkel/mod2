package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.query.TagConstantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends BaseRepository implements TagRepository {

    private SimpleJdbcInsert tagInserter;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.tagInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TagConstantQuery.TABLE_NAME).usingColumns(TagConstantQuery.NAME_COLUMN).usingGeneratedKeyColumns(TagConstantQuery.KEY);
    }

    @Override
    public long create(Tag tag) {
            Optional<Tag> isCreated = findByName(tag.getName());
            if (isCreated.isPresent()) {
                return isCreated.get().getId();
            }
            Map<String, Object> values = new HashMap<>();
            values.put(TagConstantQuery.NAME_COLUMN, tag.getName());
            return tagInserter.executeAndReturnKey(values).longValue();
    }

    @Override
    public boolean delete(Long tagId){
        return getJdbcTemplate().update(TagConstantQuery.SQL_DELETE_TAG, tagId) == 1;
    }

    @Override
    public Optional<Tag> find(Long id){
            return Optional.ofNullable(getJdbcTemplate().queryForObject(TagConstantQuery.SQL_FIND_TAG, new TagMapper(), id));
    }

    @Override
    public Optional<Tag> findByName(String tagName){
            try {return Optional.ofNullable(getJdbcTemplate().
                    queryForObject(TagConstantQuery.SQL_FIND_TAG_BY_NAME, new TagMapper(), tagName));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() {
            return getJdbcTemplate().query(TagConstantQuery.SQL_FIND_ALL_TAGS, new TagMapper());
    }

    @Override
    public List<Tag> findAllTagsByCertificateId(Long id) {
       try {
            return getJdbcTemplate().query(TagConstantQuery.SQL_FIND_ALL_TAGS_BY_CERTIFICATE_ID, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
