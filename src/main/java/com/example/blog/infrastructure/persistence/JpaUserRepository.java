package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface JpaUserRepository  extends JpaRepository<User, Long>, UserRepository{
}
