package com.epam.esm.util.DTOConverter.user;

import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.util.DTOConverter.order.OrderDTOConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {


    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        return userDTO;
    }

    public static UserDTO convertToUserDTOWithoutOrders(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }

}
