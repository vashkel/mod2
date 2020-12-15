package com.epam.esm.util.DTOConverter.user;

import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        if (!user.getOrders().isEmpty()) {
            user.getOrders().forEach(order ->
                    userDTO.getOrders().add(OrderDTOConverter.convertToOrderResponseDTO(order)));
        }
        return userDTO;
    }

    public static UserDTO convertToUserDTOWithoutOrders(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }

}
