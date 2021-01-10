package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException {
    private static final long serialVersionUID = 8668138845143990727L;
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
