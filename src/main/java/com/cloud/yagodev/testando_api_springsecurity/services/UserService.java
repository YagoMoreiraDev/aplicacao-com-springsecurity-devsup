package com.cloud.yagodev.testando_api_springsecurity.services;

import com.cloud.yagodev.testando_api_springsecurity.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
