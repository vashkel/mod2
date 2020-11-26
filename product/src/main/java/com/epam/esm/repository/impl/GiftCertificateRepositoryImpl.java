package com.epam.esm.repository.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.util.CommonParamsGiftCertificateQuery;
import com.epam.esm.repository.util.GiftCertificateParamFetcher;
import com.epam.esm.repository.util.SelectFilterCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl extends BaseRepository implements GiftCertificateRepository {

    private static String SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS = "SELECT DISTINCT c.id, c.name AS name, c.description,\n" +
            "c.price, c.create_date AS create_date, c.last_update_date, c.duration, tag.name AS tag_name FROM gift_certificate AS c LEFT JOIN\n" +
            "gift_certificate_tags AS gct ON c.id=gct.gift_certificate_id LEFT JOIN tag AS tag ON tag.id= gct.tag_id ";

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
    public Optional<List<GiftCertificate>> findAll(CommonParamsGiftCertificateQuery commonParamsGiftCertificateQuery) {
        return Optional.ofNullable(getEntityManager().createNativeQuery(SelectFilterCreator
                .createFilterQuery(GiftCertificateParamFetcher.fetchParams(commonParamsGiftCertificateQuery), SQL_BASE_SELECT_QUERY_CERTIFICATE_WITH_TAGS), GiftCertificate.class)
                .setFirstResult(commonParamsGiftCertificateQuery.getOffset())
                .setMaxResults(commonParamsGiftCertificateQuery.getLimit())
                .getResultList());
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
        getEntityManager().getTransaction().commit();
        getEntityManager().merge(giftCertificate);
        getEntityManager().getTransaction().commit();
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public Optional<List<GiftCertificate>> findByName(String name) {
            return Optional.ofNullable(getEntityManager().createNamedQuery("GiftCertificate.findByName", GiftCertificate.class).setParameter("name", name).getResultList());
    }
}

