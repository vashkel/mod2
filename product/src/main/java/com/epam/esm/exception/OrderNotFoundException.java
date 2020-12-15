package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class OrderNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public OrderNotFoundException(String message) {
        this.message = message;
    }
}
