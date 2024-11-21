package org.example.smartjob.domain.jpa_repository;

import org.example.smartjob.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRepository  extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
