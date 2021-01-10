package com.epam.esm.util.DTOConverter.registration;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.security.RegistrationResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationDTOConverter {

    public static RegistrationResponseDTO convertToRegistrationResponseDTO(User user) {
        RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();
        registrationResponseDTO.setId(user.getId());
        registrationResponseDTO.setName(user.getName());
        return registrationResponseDTO;
    }

    public static User convertToUser(RegistrationRequestDTO registrationRequestDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        User user = new User();
        user.setName(registrationRequestDTO.getName());
        user.setEmail(registrationRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        return user;
    }
}
