package com.example.blog.application.services;

import com.example.blog.domain.entities.Comment;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.CommentRepository;
import com.example.blog.domain.interfaces.PostRepository;
import com.example.blog.domain.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

//    public Comment addComment(Long postId, Long userId, Comment comment) {
//        Optional<Post> post = postRepository.findById(postId);
//        Optional<User> user = userRepository.findById(userId);
//        if (post.isPresent() && user.isPresent()) {
//            comment.setPost(post.get());
//            comment.setAuthor(user.get());
//            return commentRepository.save(comment);
//        }
//        return new Comment();
//    }
}
