package com.example.blog.domain.usecases;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.interfaces.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserPostCase {
    private PostRepository postRepository;

    public List<Post> execute(Long userId) {
        return postRepository.findByAuthorId(userId);
    }
}
