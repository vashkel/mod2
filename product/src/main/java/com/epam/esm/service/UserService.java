package com.epam.esm.service;

import com.epam.esm.modelDTO.OrderDTO;
import com.epam.esm.modelDTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    List<UserDTO> findAll();
}
