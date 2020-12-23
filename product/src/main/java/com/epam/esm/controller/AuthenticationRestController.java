package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.exception.LoginException;
import com.epam.esm.modelDTO.security.AuthenticationRequestDTO;
import com.epam.esm.modelDTO.security.AuthenticationResponseDTO;
import com.epam.esm.modelDTO.security.RegistrationRequestDTO;
import com.epam.esm.modelDTO.security.RegistrationResponseDTO;
import com.epam.esm.security.service.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    private static final String INVALID_EMAIL_PASSWORD = "locale.message.InvalidEmailPassword";
    private static final String USER_NOT_FOUND = "locale.message.UserNotFound";

    public AuthenticationRestController(
            AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> register(
            @RequestBody @Valid RegistrationRequestDTO registrationRequestDTO) {
        RegistrationResponseDTO registrationResponseDTO = userService.register(registrationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
            User user = userService.findByEmail(requestDTO.getEmail()).orElseThrow(() ->
                    new UsernameNotFoundException(USER_NOT_FOUND));
            String token = jwtTokenProvider.createToken(requestDTO.getEmail(), user.getRole().name(), user.getId());
            return ResponseEntity.ok(new AuthenticationResponseDTO(requestDTO.getEmail(), token));
        } catch (AuthenticationException e) {
            throw new LoginException(INVALID_EMAIL_PASSWORD);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
