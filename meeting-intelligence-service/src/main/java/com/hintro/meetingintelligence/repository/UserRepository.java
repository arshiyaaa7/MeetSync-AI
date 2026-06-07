package com.hintro.meetingintelligence.repository;

import com.hintro.meetingintelligence.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository
        extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email);
}