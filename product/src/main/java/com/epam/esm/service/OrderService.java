package com.epam.esm.service;

import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.CreateOrderRequestDTO;

import java.util.List;

public interface OrderService {

    CreateOrderRequestDTO findById(Long id);
    List<CreateOrderRequestDTO> findAll(int offset, int limit);
    CreateOrderRequestDTO createOrder(OrderDTO orderDTO);
}
