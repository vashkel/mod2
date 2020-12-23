package com.epam.esm.service.impl;

import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserIsNotRegistered;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.security.RegistrationResponseDTO;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.service.UserDetailsServiceImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DTOConverter.registration.RegistrationDTOConverter;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "locale.message.UserNotFound";
    private static final String USER_ALREADY_EXIST = "locale.message.UserAlreadyExist";


    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = repository.findById(id);
        UserDTO userDTO;
        if (user.isPresent()) {
            userDTO = UserDTOConverter.convertToUserDTOWithoutOrders(user.get());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return userDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findAll(int offset, int limit) {
        Optional<List<User>> users = repository.findAll(offset, limit);
        List<UserDTO> userDTOS = new ArrayList<>();
        users.ifPresent(userList -> userList.forEach(user -> userDTOS.add(UserDTOConverter.convertToUserDTOWithoutOrders(user))));
        return userDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        if (repository.findByEmail(registrationRequestDTO.getEmail()).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXIST);
        }
        User user = RegistrationDTOConverter.convertToUser(registrationRequestDTO);
        Optional<User> registeredUser = repository.save(user);
        if (registeredUser.isPresent()) {
            return RegistrationDTOConverter.convertToRegistrationResponseDTO(registeredUser.get());
        } else {
            throw new UserIsNotRegistered(USER_NOT_FOUND);
        }
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                UserDetailsServiceImpl.getAuthorities(user.getRole())
        );
    }

}
