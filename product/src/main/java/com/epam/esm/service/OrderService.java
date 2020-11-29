package com.epam.esm.service;

import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.modelDTO.OrderRepresentationDTO;

import java.util.List;

public interface OrderService {

    OrderRepresentationDTO findById(Long id);
    List<OrderRepresentationDTO> findAll(int offset, int limit);
    OrderRepresentationDTO createOrder(OrderDTO orderDTO);
}
