package com.epam.esm.service;

import com.epam.esm.modelDTO.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO findById(Long id);
    List<OrderDTO> findAll();
}
