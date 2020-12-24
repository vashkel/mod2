package com.epam.esm.security.service;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.DTOConverter.userdetails.UserDetailsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User does not exist"));
        return UserDetailsConverter.fromUser(user);
    }

    public static Set<SimpleGrantedAuthority> getAuthorities(Role role) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }
}
