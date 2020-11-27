package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);
    Optional<List<Order>> findAll();
}
