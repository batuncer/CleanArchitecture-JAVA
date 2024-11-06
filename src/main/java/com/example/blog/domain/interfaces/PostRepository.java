package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    List<Post> findAll();
    List<Post> findByAuthorId(Long authorId);
    Optional<Post> findById(Long postId);
    void deletePost(Long postId);


}
