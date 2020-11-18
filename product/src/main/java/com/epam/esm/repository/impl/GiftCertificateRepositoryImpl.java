package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.SelectFilterCreator;
import com.epam.esm.util.DurationConverter;
import com.epam.esm.util.query.CertificateConstantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl extends BaseRepository implements GiftCertificateRepository {

    private SimpleJdbcInsert giftCertificateInserter;

    @Qualifier("createEntityManager")
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.giftCertificateInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(CertificateConstantQuery.TABLE_NAME)
                .usingColumns(CertificateConstantQuery.NAME_COLUMN, CertificateConstantQuery.DESCRIPTION_COLUMN,
                        CertificateConstantQuery.PRICE_COLUMN, CertificateConstantQuery.CREATE_DATE_COLUMN,
                        CertificateConstantQuery.LAST_UPDATE_COLUMN, CertificateConstantQuery.DURATION_COLUMN)
                .usingGeneratedKeyColumns(CertificateConstantQuery.KEY);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws RepositoryException {
        try {
            GiftCertificate certificate = (GiftCertificate) entityManager
                    .createNamedQuery("GiftCertificate.findById").setParameter("id", id).getSingleResult();
            return Optional.ofNullable(certificate);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting Girt Certificate by id");
        }
    }

    @Override
    public List findAll() throws RepositoryException {
        try {
            return entityManager.createNamedQuery("GiftCertificate.findAll").getResultList();
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while getting all Gift Certificate");
        }
    }

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            entityManager.persist(giftCertificate);
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while create Gift Certificate");
        }
        return Optional.ofNullable(giftCertificate);
    }

    private Long saveGiftCertificateInfo(GiftCertificate giftCertificate) {

        DurationConverter converter = new DurationConverter();
        Map<String, Object> values = new HashMap<>();
        values.put(CertificateConstantQuery.NAME_COLUMN, giftCertificate.getName());
        values.put(CertificateConstantQuery.DESCRIPTION_COLUMN, giftCertificate.getDescription());
        values.put(CertificateConstantQuery.PRICE_COLUMN, giftCertificate.getPrice());
        values.put(CertificateConstantQuery.CREATE_DATE_COLUMN, giftCertificate.getCreateDate());
        values.put(CertificateConstantQuery.LAST_UPDATE_COLUMN, giftCertificate.getLastUpdateTime());
        values.put(CertificateConstantQuery.DURATION_COLUMN, converter.convertToDatabaseColumn(giftCertificate.getDuration()));
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
        DurationConverter converter = new DurationConverter();
        try {
            return getJdbcTemplate().update(CertificateConstantQuery.SQL_UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getLastUpdateTime(), converter.convertToDatabaseColumn(giftCertificate.getDuration()), giftCertificate.getId()) == 1;
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
            return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_CERTIFICATES_BY_PART_NAME,
                    new GiftCertificateMapper(), partName + "%");
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

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam) throws RepositoryException {
        SelectFilterCreator query = new SelectFilterCreator();
        return getJdbcTemplate().query(query.createFilterQuery(filterParam), new GiftCertificateMapper());
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) throws RepositoryException {
        try {
            GiftCertificate giftCertificate = getJdbcTemplate().queryForObject(CertificateConstantQuery.SQL_FIND_GIFT_CERTIFICATE_BY_NAME,
                    new GiftCertificateMapper(), name);
            return Optional.ofNullable(giftCertificate);
        } catch (DataAccessException e) {
            throw new RepositoryException("Exception while find gift certificate by name ");
        }
    }
}

