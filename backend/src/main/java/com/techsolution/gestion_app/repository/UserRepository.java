package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.techsolution.gestion_app.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
