package com.cloud.yagodev.testando_api_springsecurity.repositories;

import com.cloud.yagodev.testando_api_springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
