package com.epam.esm.controller;

import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.CreateOrderRequestDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<CreateOrderRequestDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CreateOrderRequestDTO>> findAll(@RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
                                                               @RequestParam(name = "limit", required = false, defaultValue = "8") int limit){
        return ResponseEntity.ok(orderService.findAll(offset, limit));
    }

    @PostMapping
    public ResponseEntity<CreateOrderRequestDTO> createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

}
