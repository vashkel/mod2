package com.epam.esm.util.DTOConverter.order;

import com.epam.esm.entity.Order;
import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;

public class OrderDTOConverter {

    public static OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCost(order.getCost());
        orderDTO.setCreateDate(order.getCreateDate());
        if (!order.getGiftCertificateSet().isEmpty()) {
            order.getGiftCertificateSet()
                    .forEach(giftCertificate -> orderDTO.getGiftCertificateSet()
                            .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        }
        return orderDTO;
    }

    public static Order convertFromOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCost(orderDTO.getCost());
        order.setCreateDate(orderDTO.getCreateDate());
        if (!orderDTO.getGiftCertificateSet().isEmpty()) {
            orderDTO.getGiftCertificateSet()
                    .forEach(giftCertificateDTO -> order.getGiftCertificateSet()
                            .add(GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO)));
        }
        return order;
    }
}
