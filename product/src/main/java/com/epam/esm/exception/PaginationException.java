package com.epam.esm.exception;

import lombok.Data;

@Data
public class PaginationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public PaginationException(String message) {
        super(message);
    }

}
