package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by email for login authentication
    Optional<User> findByEmail(String email);
}