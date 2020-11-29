package com.epam.esm.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
