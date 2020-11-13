package com.epam.esm.exception;

import org.springframework.dao.DataAccessException;

public class RepositoryException extends Exception {
    private static final long serialVersionUID = 6622597961241028408L;

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(DataAccessException cause) {
        super(cause);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
