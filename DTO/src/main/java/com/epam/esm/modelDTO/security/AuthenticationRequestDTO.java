package com.epam.esm.modelDTO.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequestDTO {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
