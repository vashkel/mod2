package com.epam.esm.controller;

import com.epam.esm.modelDTO.UserDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


}
