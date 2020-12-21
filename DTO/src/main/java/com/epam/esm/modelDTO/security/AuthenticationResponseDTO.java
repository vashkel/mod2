package com.epam.esm.modelDTO.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponseDTO {

    private String email;
    private String token;
}
