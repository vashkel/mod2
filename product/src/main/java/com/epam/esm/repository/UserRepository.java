package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /** This method is used to return user by id
     * @param id the id of user to be returned
     * @return the value of created user or empty
     */
    Optional<User> findById(Long id);

    /** This method is used to return the list of users
     * @param offset is start of users
     * @param limit limit is numbers of users
     * @return List of all Users or empty List if
     *   no Users were found
     */
    Optional<List<User>> findAll(int offset, int limit);

    /** This method is used to return user by email
     * @param email the email of user to be returned
     * @return the value of user or empty
     */
    Optional<User> findByEmail(String email);

    /**
     * This method is used to save user.
     * @param user is data for new user
     * @return created user or empty
     */
    Optional<User> save(User user);

}
