package com.epam.esm.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class LoginException extends AuthenticationException {

    public LoginException(String msg) {
        super(msg);
    }

}

