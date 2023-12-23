package com.app.domain.users_management.model.repository;

import com.app.domain.users_management.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> save(User user);
    Optional<User> findById(Long id);
}
