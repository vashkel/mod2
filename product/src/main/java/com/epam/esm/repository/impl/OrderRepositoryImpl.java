package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.BaseRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {

    public OrderRepositoryImpl(@Qualifier("createEntityManager") EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            return Optional.ofNullable(getEntityManager().createNamedQuery("Order.findById", Order.class)
                    .setParameter("id", id).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Order>> findAll(int offset, int limit) {
        return Optional.ofNullable(getEntityManager().createNamedQuery("Order.findAll", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }

    @Override
    public Optional<Order> createOrder(Order order) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(order);
        getEntityManager().getTransaction().commit();
        return Optional.ofNullable(order);
    }
}
