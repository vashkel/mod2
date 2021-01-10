package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.PasswordNotMatchException;
import com.epam.esm.exception.UserIsNotRegisteredException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.security.RegistrationResponseDTO;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.service.JwtTokenProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DTOConverter.registration.RegistrationDTOConverter;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "locale.message.UserNotFound";
    private static final String USER_ALREADY_EXIST = "locale.message.UserAlreadyExist";
    private static final String PASSWORD_NOT_MATCH = "locale.message.PasswordNotMatch";

    private final UserRepository repository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository repository, JwtTokenProvider jwtTokenProvider) {
        this.repository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
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
        users.ifPresent(userList -> userList.forEach(user ->
                userDTOS.add(UserDTOConverter.convertToUserDTOWithoutOrders(user))));
        return userDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    @Transactional
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        if (!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getRepeatedPassword())) {
            throw new PasswordNotMatchException(PASSWORD_NOT_MATCH);
        }
        if (repository.findByEmail(registrationRequestDTO.getEmail()).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXIST);
        }
        User user = RegistrationDTOConverter.convertToUser(registrationRequestDTO);
        Optional<User> registeredUser = repository.save(user);
        if (registeredUser.isPresent()) {
            return RegistrationDTOConverter.convertToRegistrationResponseDTO(registeredUser.get());
        } else {
            throw new UserIsNotRegisteredException(USER_NOT_FOUND);
        }
    }

    public boolean isUser(String token) {
        return jwtTokenProvider.getRole(token).equals("USER");
    }

}
