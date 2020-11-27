package com.epam.esm.controller;

import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

}
