package com.cloud.yagodev.testando_api_springsecurity.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", columnDefinition = "bytea", nullable = false)
    @org.hibernate.annotations.ColumnTransformer(
            read  = "pgp_sym_decrypt(name, current_setting('app.encryption_key'))::text",
            write = "pgp_sym_encrypt(?::text, current_setting('app.encryption_key'))"
    )
    private String name;

    @Column(name = "cpf", columnDefinition = "bytea", nullable = false)
    @org.hibernate.annotations.ColumnTransformer(
            read  = "pgp_sym_decrypt(cpf, current_setting('app.encryption_key'))::text",
            write = "pgp_sym_encrypt(?::text, current_setting('app.encryption_key'))"
    )
    private String cpf;
    private String password;

    @Column(name = "cpf_hash", length = 64, unique = true)
    private String cpfHash;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String id, String name, String cpf, String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpfHash() { return cpfHash; }
    public void setCpfHash(String cpfHash) { this.cpfHash = cpfHash; }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) { this.roles.remove(role); }

    public boolean hasRole(String buscarRole) {
        for(Role result : roles) {
            if(result.getRoleName().equalsIgnoreCase(buscarRole)) {
                return true;
            }
        }
        return false;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
