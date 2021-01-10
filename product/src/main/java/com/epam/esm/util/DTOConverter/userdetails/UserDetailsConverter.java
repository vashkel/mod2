package com.epam.esm.util.DTOConverter.userdetails;

import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.security.service.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsConverter {

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                UserDetailsServiceImpl.getAuthorities(user.getRole()));
    }
}
