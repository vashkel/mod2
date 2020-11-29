package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.modelDTO.OrderRepresentationDTO;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDTOConverter orderDTOConverter;

    @Transactional(readOnly = true)
    @Override
    public OrderRepresentationDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        OrderRepresentationDTO orderDTO = null;
        if (order.isPresent()) {
            orderDTO = orderDTOConverter.convertToRepresentationOrderDTO(order.get());
        } else {
            throw new OrderNotFoundException("order with " + id+ " not found");
        }
        return orderDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRepresentationDTO> findAll(int offset, int limit) {
        Optional<List<Order>> orders = orderRepository.findAll(offset, limit);
        List<OrderRepresentationDTO> orderDTOS = new ArrayList<>();
        orders.ifPresent(orderList -> orderList.forEach(order ->
                orderDTOS.add(orderDTOConverter.convertToRepresentationOrderDTO(order))));
        return orderDTOS;
    }

    @Override
    public OrderRepresentationDTO createOrder(OrderDTO orderDTO) {
        Order order = orderDTOConverter.convertToOrder(orderDTO);
        Optional<User> user = userRepository.findById(order.getUser().getId());
        Optional<Order> createdOrder = Optional.empty();
        if (user.isPresent()){
            order.setCreateDate(LocalDateTime.now());
            order.setCost(calculateOrderCost(order));
            order.setUser(user.get());
            createdOrder = orderRepository.createOrder(order);
        }else {

        }
        return orderDTOConverter.convertToRepresentationOrderDTO(createdOrder.get());
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificate().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }
}
