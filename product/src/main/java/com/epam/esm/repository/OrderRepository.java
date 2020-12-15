package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    /** This method is used to return certificate by id
     * @param id the id of User to be returned
     * @return Optional value of find User
     */
    Optional<Order> findById(Long id);

    /**
     * This method is used to return the list of certificates
     *      * @param offset is start of orders
     *      * @param limit is numbers of orders
     *      * @return List of all Users or empty List if
     *      *      no Users were found
     */
    Optional<List<Order>> findAll(int offset, int limit);

    /**
     * @param order he order to be created
     * @return the value of created user or empty
     */
    Optional<Order> createOrder(Order order);

    /**
     * @param userId is user by which will be find orders
     * @return list of users orders or empty
     */
    Optional<List<Order>> findUserOrders(Long userId);
}
