package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    /**
     * This method is used to return Order by id
     *
     * @param id the id of Order to be returned
     * @return Optional value of find Order
     */
    Optional<Order> findById(Long id);

    /**
     * This method is used to return the list of Orders
     * * @param offset is start of orders
     * * @param limit is numbers of orders
     * * @return List of all Orders or empty List if
     * *      no Orders were found
     */
    Optional<List<Order>> findAll(int offset, int limit);

    /**
     * @param order he order to be created
     * @return the value of created user or empty
     */
    Optional<Order> createOrder(Order order);

    /**
     * Method is used to return order of user by userId
     *
     * @param userId is user id
     * @return List of all orders or empty list if
     * no orders were found
     */
    Optional<List<Order>> findUserOrders(Long userId);
}
