package com.epam.esm.util.DTOConverter.user;

import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.UserDTO;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    @Autowired
    private OrderDTOConverter orderDTOConverter;

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        if (!user.getOrders().isEmpty()) {
            user.getOrders().forEach(order -> userDTO.getOrders().add(orderDTOConverter.convertToRepresentationOrderDTO(order)));
        }
        return userDTO;
    }

    public User convertFromOrderDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        if (!userDTO.getOrders().isEmpty()) {
            userDTO.getOrders().forEach(orderRepresentationDTO ->
                    user.getOrders().add(orderDTOConverter.convertToOrderFromOrderRepresentationOrderDTO(orderRepresentationDTO)));
        }
        return user;
    }

    public static UserDTO convertToUserDTOWithoutOrders(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }

    public static User convertFromOrderDTOWithoutOrders(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        return user;
    }

}
