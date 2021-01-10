package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8773401282326572433L;

    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
