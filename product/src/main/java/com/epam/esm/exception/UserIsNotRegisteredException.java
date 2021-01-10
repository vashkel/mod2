package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class UserIsNotRegisteredException extends RuntimeException {
    private static final long serialVersionUID = -5708892470943720928L;

    public UserIsNotRegisteredException(String message) {
        super(message);
    }
}
