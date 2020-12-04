package com.epam.esm.service;

import com.epam.esm.modelDTO.user.UserDTO;

import java.util.List;

public interface UserService {

    /** This method is used to return user by id
     * @param id the id of user to be returned
     * @return UserDTO
     */
    UserDTO findById(Long id);

    /** This method is used to return the list of users
     * @param offset is start of users
     * @param limit limit is numbers of users
     * @return List of all Users or empty List if
     *      *      *      no Users were found
     */
    List<UserDTO> findAll(int offset, int limit);
}
