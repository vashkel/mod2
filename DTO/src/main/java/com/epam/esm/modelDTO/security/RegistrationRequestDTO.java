package com.epam.esm.modelDTO.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationRequestDTO {
    @NotBlank
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Input Password and Submit " +
            "[ must contain 8 or more characters that are of at least one number, and one uppercase and lowercase letter]")
    private String password;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Input Password and Submit" +
            "[ must contain 8 or more characters that are of at least one number, and one uppercase and lowercase letter]")
    private String repeatedPassword;
}
