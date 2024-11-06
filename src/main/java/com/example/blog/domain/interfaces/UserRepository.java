package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    List<User> findAll();
}
