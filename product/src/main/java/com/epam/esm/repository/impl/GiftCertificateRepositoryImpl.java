package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.DurationConverter;
import com.epam.esm.util.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl extends BaseRepository implements GiftCertificateRepository {

    @Autowired
    public GiftCertificateRepositoryImpl(@Qualifier("createEntityManager")EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        GiftCertificate certificate = (GiftCertificate) getEntityManager()
                .createNamedQuery("GiftCertificate.findById").setParameter("id", id).getSingleResult();
        return Optional.ofNullable(certificate);
    }


    @Override
    public List<GiftCertificate> findAll(String hql, Pagination pagination) {
        return getEntityManager().createQuery(hql, GiftCertificate.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> create(GiftCertificate giftCertificate) {
        return Optional.ofNullable(persistWithTransaction(giftCertificate));
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
       removeWithTransaction(giftCertificate);
      }

    @Override
    public Optional<GiftCertificate> update (GiftCertificate giftCertificate) {
        DurationConverter converter = new DurationConverter();
        getEntityManager().getTransaction().commit();
        getEntityManager().merge(giftCertificate);
        getEntityManager().getTransaction().commit();
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTagName(String tag) {
//        return getJdbcTemplate().query(CertificateConstantQuery.SQL_FIND_CERTIFICATES_BY_TAG,
//                new GiftCertificateMapper(), tag);
        return null;
    }

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam) {
//        SelectFilterCreator query = new SelectFilterCreator();
//        return getJdbcTemplate().query(query.createFilterQuery(filterParam), new GiftCertificateMapper());
        return null;
    }

    @Override
    public Optional<List<GiftCertificate>> findByName(String name) {
        try {
            return Optional.ofNullable(getEntityManager().createNamedQuery("GiftCertificate.findByName", GiftCertificate.class).setParameter("name", name).getResultList());
        }catch (NoResultException e){
            return Optional.empty();
        }

    }
}

