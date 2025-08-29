package com.cloud.yagodev.testando_api_springsecurity.services;

import com.cloud.yagodev.testando_api_springsecurity.dtos.RoleRequestDTO;
import com.cloud.yagodev.testando_api_springsecurity.dtos.RoleResponseDTO;
import com.cloud.yagodev.testando_api_springsecurity.entities.Role;
import com.cloud.yagodev.testando_api_springsecurity.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RoleResponseDTO create(RoleRequestDTO dto) {
        String normalized = normalize(dto.getRoleName());

        // checar duplicidade (case-insensitive) usando o método já existente
        roleRepository.findByRoleName(normalized).ifPresent(r -> {
            throw new IllegalArgumentException("Role já existe: " + normalized);
        });

        Role role = new Role();
        role.setRoleName(normalized);
        Role saved = roleRepository.save(role);
        return new RoleResponseDTO(saved.getId(), saved.getRoleName());
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> list() {
        return roleRepository.findAll().stream()
                .map(r -> new RoleResponseDTO(r.getId(), r.getRoleName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + id));
        return new RoleResponseDTO(role.getId(), role.getRoleName());
    }

    @Transactional
    public RoleResponseDTO update(Long id, RoleRequestDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + id));

        String normalized = normalize(dto.getRoleName());

        // checar duplicidade para outro registro
        roleRepository.findByRoleName(normalized).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new IllegalArgumentException("Já existe outra role com esse nome: " + normalized);
            }
        });

        role.setRoleName(normalized);
        Role saved = roleRepository.save(role);
        return new RoleResponseDTO(saved.getId(), saved.getRoleName());
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role não encontrada: " + id);
        }
        roleRepository.deleteById(id);
    }

    private String normalize(String name) {
        return name == null ? null : name.trim().toUpperCase();
    }
}
