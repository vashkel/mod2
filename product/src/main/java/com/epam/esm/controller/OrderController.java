package com.epam.esm.controller;

import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.modelDTO.OrderRepresentationDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<OrderRepresentationDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderRepresentationDTO>> findAll( @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
                                                                 @RequestParam(name = "limit", required = false, defaultValue = "8") int limit){
        return ResponseEntity.ok(orderService.findAll(offset, limit));
    }

    @PostMapping
    public ResponseEntity<OrderRepresentationDTO> createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

}
