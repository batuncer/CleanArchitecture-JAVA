package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.interfaces.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPostRepository extends JpaRepository<Post, Long>, PostRepository {
    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deletePost(@Param("id") Long id);



}
