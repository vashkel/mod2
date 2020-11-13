package com.epam.esm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class NotValidParamsRequest extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public NotValidParamsRequest(String message) {
        this.message = message;
    }
}
