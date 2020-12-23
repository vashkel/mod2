package com.epam.esm.exception;

import lombok.Getter;

import javax.persistence.PersistenceException;

@Getter
public class UserAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = -4655222317545740804L;
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
