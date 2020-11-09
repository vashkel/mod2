package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.DurationConverter;
import com.epam.esm.util.query.CertificateConstantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
public class GiftCertificateRepositoryImpl extends BaseRepository implements GiftCertificateRepository {

    private SimpleJdbcInsert giftCertificateInserter;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.giftCertificateInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("gift_certificate")
                .usingColumns("name", "description", "price", "create_date", "last_update_date", "duration")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws RepositoryException {
        try {
            return Optional.ofNullable(getJdbcTemplate().queryForObject(CertificateConstantQuery.SQL_FIND_GIFT_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting Girt Certificate by id");
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws RepositoryException {
        try {
            return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_ALL_GIFT_CERTIFICATES, new GiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting all Gift Certificate");
        }
    }

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            Long giftCertificateId = saveGiftCertificateInfo(giftCertificate);
            giftCertificate.setId(giftCertificateId);
            giftCertificate.getTags().forEach(tag -> saveTagIdAndGiftCertificateId(tag.getId(), giftCertificateId));
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while create Gift Certificate");
        }
        return Optional.ofNullable(giftCertificate);
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
        return getJdbcTemplate().update(CertificateConstantQuery.SQL_SAVE_TAG_ID_AND_GIFT_CERTIFICATE_ID, giftCertificateId, tagId) == 1;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try {
            getJdbcTemplate().update(CertificateConstantQuery.SQL_DELETE_DEPENDED_TAG, id);
            return getJdbcTemplate().update(CertificateConstantQuery.SQL_DELETE_GIFT_CERTIFICATE, id) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while delete GiftCertificateId");
        }
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            return getJdbcTemplate().update(CertificateConstantQuery.SQL_UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getLastUpdateTime(), giftCertificate.getDuration().getSeconds(), giftCertificate.getId()) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while update GiftCertificateId");
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTagName(String tag) throws RepositoryException {
        try {
            return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_CERTIFICATES_BY_TAG, new GiftCertificateMapper(), tag);
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find gift certificate by name of tag");
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificateByPartName(String partName) throws RepositoryException {
        try {
            return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_CERTIFICATES_BY_PART_NAME, new GiftCertificateMapper(), partName + "%");
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find gift certificate by part of name ");
        }
    }

    @Override
    public List<GiftCertificate> getSortedGiftCertificates(String sortBy, String order) throws RepositoryException {
        String query = CertificateConstantQuery.SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS;
        if (sortBy != null) {
            query += "ORDER BY c." + sortBy;
        }
        if (order != null) {
            query += " " + order;
        }
        try {
            return getJdbcTemplate().query(query, new GiftCertificateMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting are filtered gift certificates ");
        }
    }

}
