package com.epam.esm.exception;

import lombok.Getter;

import javax.persistence.PersistenceException;

@Getter
public class UserAlreadyExistException extends PersistenceException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
