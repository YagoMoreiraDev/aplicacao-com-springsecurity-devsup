package com.cloud.yagodev.testando_api_springsecurity.controllers;

import com.cloud.yagodev.testando_api_springsecurity.dtos.UserRequestDTO;
import com.cloud.yagodev.testando_api_springsecurity.dtos.UserResponseDTO;
import com.cloud.yagodev.testando_api_springsecurity.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO newUser(@RequestBody UserRequestDTO requestDTO) {
        return userService.newUser(requestDTO);
    }
}
