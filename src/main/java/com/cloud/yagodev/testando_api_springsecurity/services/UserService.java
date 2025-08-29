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
import java.util.List;
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

    private static String sha256Hex(String text) {
        try {
            var md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(d.length * 2);
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar hash", e);
        }
    }

    @Transactional
    public UserResponseDTO newUser(UserRequestDTO dto) {
        // garante unicidade por hash
        String cpfHash = sha256Hex(dto.getCpf());
        if (userRepository.existsByCpfHash(cpfHash)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        User u = new User();
        u.setName(dto.getName());                                // JPA vai criptografar ao persistir
        u.setCpf(dto.getCpf());                                  // JPA vai criptografar ao persistir
        u.setCpfHash(cpfHash);                                   // índice/busca
        u.setPassword(passwordEncoder.encode(dto.getPassword())); // HASH de senha

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
        if (roles.size() != dto.getRoleIds().size()) {
            throw new IllegalArgumentException("Um ou mais roleIds inválidos.");
        }
        u.getRoles().addAll(roles);

        u = userRepository.save(u);
        return new UserResponseDTO(u); // já vem descriptografado pelo ColumnTransformer
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> buscarTodos() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .toList(); // name/cpf já saem em texto
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByCpf(String cpfPlano) {
        return userRepository.findByCpfHash(sha256Hex(cpfPlano))
                .map(UserResponseDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

}
