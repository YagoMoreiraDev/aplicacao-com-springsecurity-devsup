package com.cloud.yagodev.testando_api_springsecurity.dtos;

import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO {

    private String name;
    private String cpf;
    private String password;

    private Set<Long> roleIds = new HashSet<>();

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String cpf, String password, Set<Long> roleIds) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.roleIds = roleIds;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(Set<Long> roleIds) { this.roleIds = roleIds; }
}
