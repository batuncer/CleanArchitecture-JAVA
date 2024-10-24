package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
