package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.OrderResponseDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.service.JwtTokenProvider;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final String NOT_FOUND = "locale.message.OrderNotFound";
    private static final String USER_WHO_CREATES_ORDER_NOT_FOUND = "locale.message.UserCreatesOrderNotFound";
    private static final String GIFT_CERTIFICATE_NOT_FOUND = "locale.message.GiftCertificateNotFound";

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OrderServiceImpl(
            OrderRepository orderRepository, UserRepository userRepository,
            GiftCertificateRepository giftCertificateRepository, JwtTokenProvider jwtTokenProvider) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        OrderResponseDTO orderDTO;
        if (order.isPresent()) {
            orderDTO = OrderDTOConverter.convertToOrderResponseDTO(order.get());
        } else {
            throw new OrderNotFoundException(NOT_FOUND);
        }
        return orderDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findAll(int offset, int limit) {
        Optional<List<Order>> orders = orderRepository.findAll(offset, limit);
        List<OrderResponseDTO> orderDTOS = new ArrayList<>();
        orders.ifPresent(orderList -> orderList.forEach(order ->
                order.setCost(scaleCost(order.getCost()))));
        orders.ifPresent(orderList -> orderList.forEach(order ->
                orderDTOS.add(OrderDTOConverter.convertToOrderResponseDTO(order))));
        return orderDTOS;
    }

    private BigDecimal scaleCost(BigDecimal orderCost) {
        return orderCost.setScale(3, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException(USER_WHO_CREATES_ORDER_NOT_FOUND);
        }
        order.setUser(user.get());
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Long giftCertificateId : orderDTO.getGiftCertificates()) {
            Optional<GiftCertificate> certificateById = giftCertificateRepository.findById(giftCertificateId);
            if (!certificateById.isPresent()) {
                throw new GiftCertificateNotFoundException(GIFT_CERTIFICATE_NOT_FOUND);
            }
            giftCertificates.add(certificateById.get());
        }
        order.setGiftCertificate(giftCertificates);
        order.setCreateDate(LocalDateTime.now());
        order.setCost(calculateOrderCost(order));
        Optional<Order> createdOrder = orderRepository.createOrder(order);
        return OrderDTOConverter.convertToOrderResponseDTO(createdOrder.get());
    }

    @Override
    public List<OrderResponseDTO> findUserOrders(Long userId) {
        List<OrderResponseDTO> userOrders = new ArrayList<>();
        if (isRegisteredUser(userId)) {
            Optional<List<Order>> orders = orderRepository.findUserOrders(userId);
            orders.ifPresent(orderList -> orderList.forEach(order ->
                    order.setCost(scaleCost(order.getCost()))));
            orders.ifPresent(orderList -> orderList.forEach(order ->
                    userOrders.add(OrderDTOConverter.convertToOrderResponseDTO(order))));
        }
        return userOrders;
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificate().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP), BigDecimal::add);
    }

    private boolean isRegisteredUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent();
    }

    public boolean isUser(String token) {
        return jwtTokenProvider.getRole(token).equals("USER");
    }
}
