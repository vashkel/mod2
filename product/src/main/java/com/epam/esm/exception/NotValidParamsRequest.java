package com.epam.esm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotValidParamsRequest extends RuntimeException {
    private static final long serialVersionUID = -2654762026200857171L;

    private String message;

    public NotValidParamsRequest(String message) {
        this.message = message;
    }
}
