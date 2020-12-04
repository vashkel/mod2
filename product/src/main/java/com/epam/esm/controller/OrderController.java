package com.epam.esm.controller;

import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.OrderResponseDTO;
import com.epam.esm.modelDTO.order.UsersOrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDTO> findById(
            @PathVariable  @Min(value = 1, message = "id must be 1 or grater then 1") Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UsersOrderDTO>> findByUserId(
            @PathVariable @Min(value = 1, message = "id must be 1 or grater then 1") Long userId){
        return ResponseEntity.ok(orderService.findUserOrders(userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll(
            @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") int limit){
        return ResponseEntity.ok(orderService.findAll(offset, limit));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

}
