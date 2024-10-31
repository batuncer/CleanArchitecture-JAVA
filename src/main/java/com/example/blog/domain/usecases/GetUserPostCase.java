package com.example.blog.domain.usecases;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.interfaces.PostRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUserPostCase {
    private PostRepository postRepository;

    public List<Post> execute(Long userId) {
        return postRepository.findByAuthorId(userId);
    }
}
