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

    private static final String THE_MOST_POPULAR_TAG_USER_WITH_HIGHEST_PRICE_OF_ORDER =
            "SELECT tag.id , COUNT(tag.id) AS 'numberOfTag', tag.name, user_id FROM tag\n" +
            "           LEFT JOIN gift_certificate_tags gct on tag.id = gct.tag_id\n" +
            "           LEFT JOIN gift_certificate gc on gct.gift_certificate_id = gc.id\n" +
            "           LEFT JOIN users_order_gift_certificate uogc on gc.id = uogc.gift_certificate_id\n" +
            "           LEFT JOIN users_order uo on uogc.order_id = uo.id\n" +
            "         WHERE user_id = (SELECT user_id FROM users_order GROUP BY users_order.user_id " +
                    "ORDER BY SUM(users_order.cost) DESC LIMIT 1)\n" +
            "         GROUP BY tag.id ORDER BY numberOfTag DESC LIMIT 1;";
    private static final String DELETE_FROM_GIFT_CERTIFICATE_TAGS = "DELETE FROM gift_certificate_tags WHERE tag_id = ?";

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
            return Optional.ofNullable(getEntityManager().createNamedQuery("Tag.findById", Tag.class)
                    .setParameter("id", id).getSingleResult());
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
        Tag tag = (Tag) getEntityManager()
                .createNativeQuery(THE_MOST_POPULAR_TAG_USER_WITH_HIGHEST_PRICE_OF_ORDER, Tag.class)
                .getSingleResult();
        return Optional.ofNullable(tag);
    }

    public void deleteFromGiftCertificateTagTable(Long tagId){
        getEntityManager()
                .createNativeQuery(DELETE_FROM_GIFT_CERTIFICATE_TAGS)
                .setParameter(1, tagId)
                .executeUpdate();
    }
}
