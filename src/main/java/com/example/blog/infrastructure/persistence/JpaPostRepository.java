package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaPostRepository extends JpaRepository<Post, Long>, PostRepository {
    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deletePost(@Param("id") Long id);

//    @Query("SELECT p FROM Post p WHERE p.date IN :dates AND p.author IN :friends ORDER BY p.date DESC")
//    List<Post> findFriendPostsByDate(@Param("dates") List<LocalDate> dates,
//                                     @Param("friends") List<User> friends,
//                                     Pageable pageable);

}
