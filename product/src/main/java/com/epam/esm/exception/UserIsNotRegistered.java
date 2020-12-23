package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class UserIsNotRegistered extends RuntimeException {
    private static final long serialVersionUID = -5708892470943720928L;

    public UserIsNotRegistered(String message) {
        super(message);
    }
}
