package com.example.blog.domain.usecases;

import com.example.blog.application.dto.PostResponse;
import com.example.blog.application.mapper.PostMapper;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.PostRepository;
import com.example.blog.domain.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreatePostUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Autowired
    public CreatePostUseCase(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    // Yeni post oluşturur ve DTO formatında döner
    public PostResponse execute(String content, Long userId ) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(user);
        post.setImage(user.getProfilePicture());
        post.setDate(LocalDateTime.now());

        // Post'u kaydet ve DTO'ya dönüştür
        Post savedPost = postRepository.save(post);
        return postMapper.toPostResponse(savedPost);
    }

    // Tüm postları DTO formatında döner
    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    // Belirli bir postu DTO formatında döner
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toPostResponse(post);
    }

    // Belirli bir yazarın postlarını DTO formatında döner
    public List<PostResponse> getPostsByAuthorId(Long authorId) {
        List<Post> posts = postRepository.findByAuthorId(authorId);
        return posts.stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    // Postu siler
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
