package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Role;
import com.example.blog.domain.interfaces.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaRoleRepository extends JpaRepository<Role, Long>, RoleRepository {
}
