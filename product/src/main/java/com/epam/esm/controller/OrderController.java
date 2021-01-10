package com.epam.esm.controller;

import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.OrderResponseDTO;
import com.epam.esm.security.service.JwtTokenProvider;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public OrderController(OrderService orderService, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.orderService = orderService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<OrderResponseDTO> findById(
            @PathVariable @Min(value = 1, message = "id must be 1 or grater then 1") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(
            @PathVariable @Min(value = 1, message = "id must be 1 or grater then 1") Long userId) {

        return ResponseEntity.ok(orderService.findUserOrders(userId));
    }

    @GetMapping("my-orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponseDTO>> findUserOrders(HttpServletRequest httpRequest){
        String token = jwtTokenProvider.resolveToken(httpRequest);
        Long userId = jwtTokenProvider.getUserId(token);
        return ResponseEntity.ok(orderService.findUserOrders(userId));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponseDTO>> findAll(
            @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") @Min(value = 1) int limit) {
        return ResponseEntity.ok(orderService.findAll(offset, limit));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

}
