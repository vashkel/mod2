package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.util.SelectFilterCreator;
import com.epam.esm.util.DurationConverter;
import com.epam.esm.util.query.CertificateConstantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public Optional<GiftCertificate> findById(Long id) {
        GiftCertificate certificate = (GiftCertificate) entityManager
                .createNamedQuery("GiftCertificate.findById").setParameter("id", id).getSingleResult();
        return Optional.ofNullable(certificate);
    }


    @Override
    public List<GiftCertificate> findAll(String hql, Pagination pagination) {
        return entityManager.createQuery(hql, GiftCertificate.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) {
        entityManager.getTransaction().begin();
        entityManager.persist(giftCertificate);
        entityManager.getTransaction().commit();
        return Optional.ofNullable(giftCertificate);
    }

//    private Long saveGiftCertificateInfo(GiftCertificate giftCertificate) {
//        DurationConverter converter = new DurationConverter();
//        Map<String, Object> values = new HashMap<>();
//        values.put(CertificateConstantQuery.NAME_COLUMN, giftCertificate.getName());
//        values.put(CertificateConstantQuery.DESCRIPTION_COLUMN, giftCertificate.getDescription());
//        values.put(CertificateConstantQuery.PRICE_COLUMN, giftCertificate.getPrice());
//        values.put(CertificateConstantQuery.CREATE_DATE_COLUMN, giftCertificate.getCreateDate());
//        values.put(CertificateConstantQuery.LAST_UPDATE_COLUMN, giftCertificate.getLastUpdateTime());
//        values.put(CertificateConstantQuery.DURATION_COLUMN,
//                converter.convertToDatabaseColumn(giftCertificate.getDuration()));
//        return giftCertificateInserter.executeAndReturnKey(values).longValue();
//    }
//
//    private boolean saveTagIdAndGiftCertificateId(long tagId, long giftCertificateId) {
//        return getJdbcTemplate()
//                .update(CertificateConstantQuery.SQL_SAVE_TAG_ID_AND_GIFT_CERTIFICATE_ID, giftCertificateId, tagId) == 1;
//    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.getTransaction().begin();
        entityManager.remove(giftCertificate);
        entityManager.getTransaction().commit();
      }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        DurationConverter converter = new DurationConverter();
        return getJdbcTemplate().update(CertificateConstantQuery.SQL_UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getLastUpdateTime(),
                converter.convertToDatabaseColumn(giftCertificate.getDuration()), giftCertificate.getId()) == 1;
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTagName(String tag) {
        return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_CERTIFICATES_BY_TAG,
                new GiftCertificateMapper(), tag);
    }

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam) {
        SelectFilterCreator query = new SelectFilterCreator();
        return getJdbcTemplate().query(query.createFilterQuery(filterParam), new GiftCertificateMapper());
    }

    @Override
    public Optional<List<GiftCertificate>> findByName(String name) {
        return Optional.ofNullable(entityManager.createQuery("FROM GiftCertificate WHERE name = :name", GiftCertificate.class).setParameter("name", name).getResultList());
//            return Optional.ofNullable(getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_GIFT_CERTIFICATE_BY_NAME,
//                    new GiftCertificateMapper(), name));

    }
}

