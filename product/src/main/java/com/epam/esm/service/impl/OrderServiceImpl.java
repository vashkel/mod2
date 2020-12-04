package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.OrderResponseDTO;
import com.epam.esm.modelDTO.order.UsersOrderDTO;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final String NOT_FOUND = "locale.message.OrderNotFound";
    private static final String USER_WHO_CREATES_ORDER_NOT_FOUND = "locale.message.UserCreatesOrderNotFound";

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDTOConverter orderDTOConverter;

    public OrderServiceImpl(
            OrderRepository orderRepository, UserRepository userRepository, OrderDTOConverter orderDTOConverter) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDTOConverter = orderDTOConverter;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        OrderResponseDTO orderDTO;
        if (order.isPresent()) {
            orderDTO = orderDTOConverter.convertToOrderResponseDTO(order.get());
        } else {
            throw new OrderNotFoundException(NOT_FOUND);
        }
        return orderDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAll(int offset, int limit) {
        Optional<List<Order>> orders = orderRepository.findAll(offset, limit);
        List<OrderResponseDTO> orderDTOS = new ArrayList<>();
        orders.ifPresent(orderList -> orderList.forEach(order ->
                orderDTOS.add(orderDTOConverter.convertToOrderResponseDTO(order))));
        return orderDTOS;
    }

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order order = orderDTOConverter.convertToOrder(orderDTO);
        Optional<User> user = userRepository.findById(order.getUser().getId());
        Optional<Order> createdOrder;
        if (user.isPresent()) {
            order.setCreateDate(LocalDateTime.now());
            order.setCost(calculateOrderCost(order));
            order.setUser(user.get());
            createdOrder = orderRepository.createOrder(order);
        } else {
            throw new UserNotFoundException(USER_WHO_CREATES_ORDER_NOT_FOUND);
        }
        return orderDTOConverter.convertToOrderResponseDTO(createdOrder.get());
    }

    @Override
    public List<UsersOrderDTO> findUserOrders(Long userId) {
        List<UsersOrderDTO> userOrders = new ArrayList<>();
        if (isRegisteredUser(userId)) {
            Optional<List<Order>> orders = orderRepository.findUserOrders(userId);
            orders.ifPresent(orderList -> orderList.forEach(order ->
                    userOrders.add(orderDTOConverter.convertToUserOrdersDTO(order))));
        }
        return userOrders;
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificate().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }

    private boolean isRegisteredUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent();
    }
}
