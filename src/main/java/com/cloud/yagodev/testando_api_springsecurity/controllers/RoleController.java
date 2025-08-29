package com.cloud.yagodev.testando_api_springsecurity.controllers;

import com.cloud.yagodev.testando_api_springsecurity.dtos.RoleRequestDTO;
import com.cloud.yagodev.testando_api_springsecurity.dtos.RoleResponseDTO;
import com.cloud.yagodev.testando_api_springsecurity.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> create(@RequestBody RoleRequestDTO dto) {
        RoleResponseDTO created = roleService.create(dto);
        return ResponseEntity.created(URI.create("/api/roles/" + created.id())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> list() {
        return ResponseEntity.ok(roleService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> find(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
