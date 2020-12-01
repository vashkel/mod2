package com.epam.esm.service;

import com.epam.esm.modelDTO.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    List<UserDTO> findAll(int offset, int limit);
}
