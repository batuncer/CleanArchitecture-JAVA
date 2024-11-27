package com.example.blog.adapters.controllers;

import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.application.dto.PostResponse;
import com.example.blog.application.mapper.PostMapper;
import com.example.blog.application.services.PostService;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@CrossOrigin(origins= "http://localhost:3000")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostController(PostService postService, UserService userService, PostMapper postMapper) {
        this.postService = postService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getPicture());
        post.setAuthor(user);
        post.setDate(LocalDateTime.now());
        Post savedPost = postService.savePost(post);
        PostResponse response = postMapper.toPostResponse(savedPost);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(postMapper.toPostResponse(post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByAuthorId(userId);
        List<PostResponse> responses = posts.stream()
                .map(postMapper::toPostResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
            List<Post> posts = (List<Post>) postService.getPosts();
            List<PostResponse> responses = posts.stream()
                    .map(postMapper::toPostResponse)
                    .toList();
            return ResponseEntity.ok(responses);
        }
}
