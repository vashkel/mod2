package com.epam.esm.util.DTOConverter.order;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.order.OrderResponseDTO;
import com.epam.esm.modelDTO.order.OrderDTO;
import com.epam.esm.modelDTO.order.UsersOrderDTO;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDTOConverter {

    public OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCost(order.getCost());
        orderDTO.setCreateDate(order.getCreateDate());
        if (!order.getGiftCertificate().isEmpty()) {
            order.getGiftCertificate()
                    .forEach(giftCertificate -> orderDTO.getGiftCertificates()
                            .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        }
        if (order.getUser() != null) {
            orderDTO.setUser(UserDTOConverter.convertToUserDTOWithoutOrders(order.getUser()));
        }
        return orderDTO;
    }

    public UsersOrderDTO convertToUserOrdersDTO(Order order){
        UsersOrderDTO usersOrderDTO = new UsersOrderDTO();
        usersOrderDTO.setCreateDate(order.getCreateDate());
        usersOrderDTO.setCost(order.getCost());
        return usersOrderDTO;
    }

    public Order convertToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        User user = new User();
        user.setId(orderDTO.getUserId());
        order.setUser(user);
        if (!orderDTO.getGiftCertificates().isEmpty()) {
            orderDTO.getGiftCertificates()
                    .forEach(giftCertificateDTO -> order.getGiftCertificate()
                            .add(GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO)));
        }
        return order;
    }

    }

