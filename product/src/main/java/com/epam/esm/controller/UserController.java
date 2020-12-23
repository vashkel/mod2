package com.epam.esm.controller;

import com.epam.esm.modelDTO.user.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> findById(
            @PathVariable @Min(value = 1, message = "id must be 1 or grater then 1") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(name = "offset", required = false, defaultValue = "1") @Min(value = 1) int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "8") int limit) {
        return ResponseEntity.ok(userService.findAll(offset, limit));
    }

}
