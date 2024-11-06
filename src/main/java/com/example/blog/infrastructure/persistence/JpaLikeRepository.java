package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Like;
import com.example.blog.domain.interfaces.LikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaLikeRepository  extends JpaRepository<Like, Long> , LikeRepository {

}
