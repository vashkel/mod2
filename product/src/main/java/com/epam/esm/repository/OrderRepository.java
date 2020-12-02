package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);
    Optional<List<Order>> findAll(int offset, int limit);
    Optional<Order> createOrder(Order order);

    Optional<List<Order>> findUserOrders(Long userId);
}
