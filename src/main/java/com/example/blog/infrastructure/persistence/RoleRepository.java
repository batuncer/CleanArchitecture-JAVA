package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
}
