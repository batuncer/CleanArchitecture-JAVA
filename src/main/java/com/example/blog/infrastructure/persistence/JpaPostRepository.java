package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.interfaces.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPostRepository extends JpaRepository<Post, Long>, PostRepository {

}
