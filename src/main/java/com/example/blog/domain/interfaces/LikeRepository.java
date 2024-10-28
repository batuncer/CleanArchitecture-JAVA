package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPostId(Long postId);
    List<Like> findByCommentId(Long commentId);
}
