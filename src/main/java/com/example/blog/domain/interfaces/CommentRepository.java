package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.Comment;
import com.example.blog.domain.entities.Like;

import java.util.List;


public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findAll();
    Comment findById(Long id);
    void deleteById(Long id);
    List<Like> findLikeByCommentId(Long id);
    List<Like> findLikeByCommentIdAndUserId(Long id, Long userId);
    List<Comment> findCommentByCommentIdAndUserId(Long id, Long userId);

}
