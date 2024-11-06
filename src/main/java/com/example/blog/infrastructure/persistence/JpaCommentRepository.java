package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Comment;
import com.example.blog.domain.interfaces.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommentRepository extends JpaRepository<Comment, Long>,CommentRepository {
}
