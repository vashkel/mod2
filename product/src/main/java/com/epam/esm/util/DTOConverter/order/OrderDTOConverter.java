package com.epam.esm.util.DTOConverter.order;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.modelDTO.OrderRepresentationDTO;
import com.epam.esm.util.DTOConverter.certificate.GiftCertificateDTOConverter;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDTOConverter {

    @Autowired
    private final ModelMapper modelMapper;

    public OrderRepresentationDTO convertToRepresentationOrderDTO(Order order) {
        OrderRepresentationDTO orderDTO = new OrderRepresentationDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCost(order.getCost());
        orderDTO.setCreateDate(order.getCreateDate());
        if (!order.getGiftCertificate().isEmpty()) {
            order.getGiftCertificate()
                    .forEach(giftCertificate -> orderDTO.getGiftCertificates()
                            .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
        }
        if (order.getUser()!=null){
            orderDTO.setUser(UserDTOConverter.convertToUserDTOWithoutOrders(order.getUser()));
        }
        return orderDTO;
    }

//    public static Order convertFromOrderDTO(OrderRepresentationDTO orderDTO){
//        Order order = new Order();
//        order.setId(orderDTO.getId());
//        order.setCost(orderDTO.getCost());
//        order.setCreateDate(orderDTO.getCreateDate());
//        if (!orderDTO.getGiftCertificateSet().isEmpty()) {
//            orderDTO.getGiftCertificateSet()
//                    .forEach(giftCertificateDTO -> order.getGiftCertificateSet()
//                            .add(GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO)));
//        }
//        return order;
//    }

//    public static OrderRepresentationDTO convertToOrderDTO(Order order) {
//        OrderRepresentationDTO orderDTO = new OrderRepresentationDTO();
//        orderDTO.setId(order.getId());
//        orderDTO.setCost(order.getCost());
//        orderDTO.setCreateDate(order.getCreateDate());
//        if (!order.getGiftCertificateSet().isEmpty()) {
//            order.getGiftCertificateSet()
//                    .forEach(giftCertificate -> orderDTO.getGiftCertificateSet()
//                            .add(GiftCertificateDTOConverter.convertToGiftCertificateDTO(giftCertificate)));
//        }
//        return orderDTO;
//    }

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

    public Order convertToOrderFromOrderRepresentationOrderDTO(OrderRepresentationDTO orderRepresentationDTO) {
        Order order = new Order();
        order.setId(orderRepresentationDTO.getId());
        order.setCost(orderRepresentationDTO.getCost());
        order.setCreateDate(orderRepresentationDTO.getCreateDate());
        if (!orderRepresentationDTO.getGiftCertificates().isEmpty()) {
            orderRepresentationDTO.getGiftCertificates()
                    .forEach(giftCertificateDTO -> order.getGiftCertificate()
                            .add(GiftCertificateDTOConverter.convertFromGiftCertificateDTO(giftCertificateDTO)));
        }
        return order;
    }
}
