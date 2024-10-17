package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    User save(User user);
    User findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
}
