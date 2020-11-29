package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    Optional<List<User>> findAll(int offset, int limit);
}
