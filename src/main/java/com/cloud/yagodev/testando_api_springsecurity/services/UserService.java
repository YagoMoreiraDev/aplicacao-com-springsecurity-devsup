package com.cloud.yagodev.testando_api_springsecurity.services;

import com.cloud.yagodev.testando_api_springsecurity.dtos.UserRequestDTO;
import com.cloud.yagodev.testando_api_springsecurity.dtos.UserResponseDTO;
import com.cloud.yagodev.testando_api_springsecurity.entities.Role;
import com.cloud.yagodev.testando_api_springsecurity.entities.User;
import com.cloud.yagodev.testando_api_springsecurity.repositories.RoleRepository;
import com.cloud.yagodev.testando_api_springsecurity.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO newUser(UserRequestDTO userRequestDTO) {

        User newUser = new User();
        newUser.setName(userRequestDTO.getName());
        newUser.setCpf(userRequestDTO.getCpf());
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        // busca todas as roles pelos IDs do request
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userRequestDTO.getRoleIds()));
        if (roles.size() != userRequestDTO.getRoleIds().size()) {
            throw new IllegalArgumentException("Um ou mais roleIds inválidos.");
        }
        newUser.getRoles().addAll(roles);

        newUser = userRepository.save(newUser);

        return new UserResponseDTO(newUser);
    }

}
