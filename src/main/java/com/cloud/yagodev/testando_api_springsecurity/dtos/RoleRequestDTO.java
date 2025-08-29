package com.cloud.yagodev.testando_api_springsecurity.dtos;

public class RoleRequestDTO {

    private String roleName;

    public RoleRequestDTO() {}

    public RoleRequestDTO(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
