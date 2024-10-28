package com.example.blog.application.services;


import com.example.blog.domain.entities.Like;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.LikeRepository;
import com.example.blog.domain.interfaces.PostRepository;
import com.example.blog.domain.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private LikeRepository likeRepository;

    private PostRepository postRepository;

    private UserRepository userRepository;

    // like a post
    public Like likePost(Long postId, Long userId, Like like) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);
        if (post.isPresent() && user.isPresent()) {
            like.setPost(post.get());
            like.setUser(user.get());
            return likeRepository.save(like);
        }
        return null;
    }

    // delete like
    public void unlike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public List<Like> findAll() {
        return likeRepository.findAll();
    }
}
