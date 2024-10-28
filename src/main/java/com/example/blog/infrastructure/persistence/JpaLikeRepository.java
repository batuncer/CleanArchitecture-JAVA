package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaLikeRepository  extends JpaRepository<Like, Long> {
}
