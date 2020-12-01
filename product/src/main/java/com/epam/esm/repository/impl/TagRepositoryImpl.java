package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends BaseRepository implements TagRepository {

    private static final String THE_MOST_POPULAR_TAG_USER_WITH_HIGHEST_PRICE_OF_ORDER = "select tag.id , COUNT(tag.id) as 'tagID', tag.name, user_id, uo.cost from tag\n"+
            "LEFT JOIN gift_certificate_tags gct on tag.id = gct.tag_id\n"+
            "LEFT JOIN gift_certificate gc on gct.gift_certificate_id = gc.id\n"+
            "LEFT JOIN users_order_gift_certificate uogc on gc.id = uogc.gift_certificate_id\n"+
            "LEFT JOIN users_order uo on uogc.order_id = uo.id\n"+
            "where user_id = (select user_id from users_order\n"+
            "where users_order.cost = ( select max(cost) from giftcertificate.users_order))\n"+
            "group by tag.id order by tagID DESC LIMIT 1";

    @Autowired
    public TagRepositoryImpl(@Qualifier("createEntityManager") EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Tag> create(Tag tag) {
        getEntityManager().persist(tag);
        return Optional.ofNullable(tag);
    }

    @Override
    public void delete(Tag tag) {
        getEntityManager().remove(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(getEntityManager().createNamedQuery("Tag.findById", Tag.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        try {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findByName", Tag.class)
                    .setParameter("name", tagName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Tag>> findAll(int offset, int limit) {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findAll", Tag.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList());
    }

    @Override
    public Optional<List<Tag>> findAllTagsByCertificateId(Long id) {
            return Optional.ofNullable(getEntityManager()
                    .createNamedQuery("Tag.findAllTagsByCertificateId", Tag.class)
                    .setParameter("id", id)
                    .getResultList());
    }

    @Override
    public Optional<Tag> findMostPopularTagOfUserWithHighestPriceOfOrders() {
        Tag tag = (Tag) getEntityManager().createNativeQuery(THE_MOST_POPULAR_TAG_USER_WITH_HIGHEST_PRICE_OF_ORDER, Tag.class).getSingleResult();
        return Optional.ofNullable(tag);
    }
}
