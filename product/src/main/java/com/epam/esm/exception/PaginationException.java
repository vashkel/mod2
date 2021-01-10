package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaginationException extends RuntimeException {
    private static final long serialVersionUID = -1519406983344757977L;

    private String message;

    public PaginationException(String message) {
        super(message);
    }

}
