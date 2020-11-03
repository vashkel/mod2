package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.GiftCertificateWIthTagsMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.DurationConverter;
import exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl extends BaseRepository implements GiftCertificateRepository {

    private SimpleJdbcInsert giftCertificateInserter;
    private TagRepository tagRepository;

    @Autowired
    public GiftCertificateRepositoryImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.giftCertificateInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("gift_certificate")
                .usingColumns("name", "description", "price", "create_date", "last_update_date", "duration")
                .usingGeneratedKeyColumns("id");
    }

    private final String SQL_FIND_CERTIFICATES_BY_TAG = "select c.id, c.name, c.description, c.price, c.create_date, c.last_update_date, c.duration, tag.id, tag.name \n" +
            "FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags AS gct\n" +
            "ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id WHERE tag.name=?;";
    private final String SQL_FIND_CERTIFICATES_BY_PART_NAME = "select c.id, c.name, c.description, c.price, c.create_date, c.last_update_date, c.duration, tag.id, tag.name \n" +
            "FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags AS gct\n" +
            "ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id WHERE c.name LIKE ?";
    private final String SQL_FIND_GIFT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, create_date, last_update_date, duration  FROM gift_certificate WHERE id=?";
    private final String SQL_FIND_ALL_GIFT_CERTIFICATES = "SELECT id, name, description, price, create_date, last_update_date, duration  FROM gift_certificate";
    private final String SQL_SAVE_TAG_ID_AND_GIFT_CERTIFICATE_ID = "INSERT INTO gift_certificate_tags(gift_certificate_id,tag_id) VALUES (?,?)";
    private final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private final String SQL_DELETE_DEPENDED_TAG = "DELETE FROM gift_certificate_tags WHERE gift_certificate_id =?";
    private final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name =?, description=?, price=?, last_update_date=?, duration=? WHERE id=?";
    private final String SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS = "select c.id, c.name, c.description, c.price, c.create_date, c.last_update_date, c.duration, tag.id, tag.name " +
            "FROM gift_certificate AS c LEFT JOIN  gift_certificate_tags AS gct " +
            "ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id ";

    @Override
    public GiftCertificate findGiftCertificateById(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_GIFT_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting Girt Certificate by id");
        }
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificates() throws RepositoryException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_GIFT_CERTIFICATES, new GiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting all Gift Certificate");
        }
    }

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            for (Tag insertedTag : giftCertificate.getTags()) {
                long tagID = tagRepository.create(insertedTag);
                insertedTag.setId(tagID);
            }
            Long giftCertificateId = saveGiftCertificateInfo(giftCertificate);
            giftCertificate.setId(giftCertificateId);
            giftCertificate.getTags().forEach(tag -> saveTagIdAndGiftCertificateId(tag.getId(), giftCertificateId));
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while create Gift Certificate");
        }
        return giftCertificate;
    }

    private Long saveGiftCertificateInfo(GiftCertificate giftCertificate) {
        DurationConverter converter = new DurationConverter();
        Map<String, Object> values = new HashMap<>();
        values.put("name", giftCertificate.getName());
        values.put("description", giftCertificate.getDescription());
        values.put("price", giftCertificate.getPrice());
        values.put("create_date", giftCertificate.getCreateDate());
        values.put("last_update_date", giftCertificate.getLastUpdateTime());
        values.put("duration", converter.convertToDatabaseColumn(giftCertificate.getDuration()));
        return giftCertificateInserter.executeAndReturnKey(values).longValue();
    }

    private boolean saveTagIdAndGiftCertificateId(long tagId, long giftCertificateId) {
        return jdbcTemplate.update(SQL_SAVE_TAG_ID_AND_GIFT_CERTIFICATE_ID, giftCertificateId, tagId) == 1;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try {
            jdbcTemplate.update(SQL_DELETE_DEPENDED_TAG, id);
            return jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, id)==1;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while delete GiftCertificateId");
        }
    }

    @Override
    public long update(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            return jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getLastUpdateTime(), giftCertificate.getDuration().getSeconds(), giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while update GiftCertificateId");
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTagName(String tag) throws RepositoryException {
        try {
            return jdbcTemplate.query(SQL_FIND_CERTIFICATES_BY_TAG, new GiftCertificateWIthTagsMapper(), tag);
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find gift certificate by name of tag");
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificateByPartName(String partName) throws RepositoryException {
        try {
            return jdbcTemplate.query(SQL_FIND_CERTIFICATES_BY_PART_NAME, new GiftCertificateWIthTagsMapper(), partName + "%");
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find gift certificate by part of name ");
        }
    }

    @Override
    public List<GiftCertificate> getFilteredGiftCertificates(String sortBy, String order) throws RepositoryException {
        String query = SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS;
        if(sortBy != null) {
            query += "ORDER BY " + sortBy;
        }
            if(order != null){
                query += " " + order;
            }
         try {
             return jdbcTemplate.query(query, new GiftCertificateWIthTagsMapper());
         }catch (DataAccessException e) {
             throw new RepositoryException("Exception while getiing are filtered gift certificates ");
         }
    }


}
