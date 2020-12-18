package com.epam.esm.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role {

    GUEST, USER, ADMIN;

    public static Set<SimpleGrantedAuthority> getAuthorities(Role role) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }
}
