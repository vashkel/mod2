package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.UserAlreadyExistException;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.security.RegistrationResponseDTO;
import com.epam.esm.modelDTO.user.UserDTO;

import java.util.List;
import java.util.Optional;

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

    /** This method is used to return user by email
     * @param email the email of user to be returned
     * @return the value of user or empty
     */
    Optional<User> findByEmail(String email);

    /**
     * This method is used to register user.
     * @param registrationRequestDTO is data for new user
     * @return created user
     */
    RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO);


    /** This is method is used to check isUser or not
     * @param token  is token of client
     * @return true if user, false if not
     */
    boolean isUser(String token);
}
