package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DTOConverter.user.UserDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND = "locale.message.UserNotFound";

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = repository.findById(id);
        UserDTO userDTO;
        if (user.isPresent()) {
            userDTO = UserDTOConverter.convertToUserDTOWithoutOrders(user.get());
        } else {
            throw new UserNotFoundException(NOT_FOUND);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> findAll(int offset, int limit) {
        Optional<List<User>> users = repository.findAll(offset, limit);
        List<UserDTO> userDTOS = new ArrayList<>();
        users.ifPresent(userList -> userList.forEach(user -> userDTOS.add(UserDTOConverter.convertToUserDTOWithoutOrders(user))));
        return userDTOS;
    }

}
