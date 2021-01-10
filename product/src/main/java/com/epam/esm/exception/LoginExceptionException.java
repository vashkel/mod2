package com.epam.esm.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class LoginExceptionException extends AuthenticationException {

    public LoginExceptionException(String msg) {
        super(msg);
    }

}

