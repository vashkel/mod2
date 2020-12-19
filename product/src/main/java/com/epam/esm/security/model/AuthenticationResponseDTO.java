package com.epam.esm.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class AuthenticationResponseDTO {

    private String email;
    private String token;
}
