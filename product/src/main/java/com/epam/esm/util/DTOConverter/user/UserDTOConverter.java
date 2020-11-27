package com.epam.esm.util.DTOConverter.user;

import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.UserDTO;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;

public class UserDTOConverter {

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        if (!user.getOrders().isEmpty()) {
            user.getOrders().forEach(order -> userDTO.getOrders().add(OrderDTOConverter.convertToOrderDTO(order)));
        }
        return userDTO;
    }

    public static User convertFromOrderDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        if (!userDTO.getOrders().isEmpty()) {
            userDTO.getOrders().forEach(orderDTO -> user.getOrders().add(OrderDTOConverter.convertFromOrderDTO(orderDTO)));
        }
        return user;
    }

}
