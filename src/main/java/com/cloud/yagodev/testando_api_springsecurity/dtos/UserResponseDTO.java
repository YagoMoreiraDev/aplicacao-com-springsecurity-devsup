package com.cloud.yagodev.testando_api_springsecurity.dtos;

import com.cloud.yagodev.testando_api_springsecurity.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserResponseDTO(
        String id,
        String name,
        String cpf,
        Set<RoleResponseDTO> roles
) {
    public UserResponseDTO(User entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getRoles() == null ? Set.of() :
                        entity.getRoles().stream()
                                .map(r -> new RoleResponseDTO(r.getId(), r.getRoleName()))
                                .collect(Collectors.toUnmodifiableSet()));
    }
}
