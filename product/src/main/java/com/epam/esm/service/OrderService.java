package com.epam.esm.service;

import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO findById(Long id);

    List<OrderResponseDTO> findAll(int offset, int limit);

    OrderResponseDTO createOrder(OrderDTO orderDTO);

    List<OrderResponseDTO> findUserOrders(Long userId);

    boolean isUser(String token);
}
