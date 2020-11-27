package com.epam.esm.service.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        OrderDTO orderDTO = null;
        if (order.isPresent()) {
            orderDTO = OrderDTOConverter.convertToOrderDTO(order.get());
        } else {

        }
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAll() {
        Optional<List<Order>> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orders.ifPresent(orderList -> orderList.forEach(order ->
                orderDTOS.add(OrderDTOConverter.convertToOrderDTO(order))));
        return orderDTOS;
    }
}
