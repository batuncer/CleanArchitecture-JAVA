package com.example.blog.adapters.controllers;


import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.application.dto.PostResponse;
import com.example.blog.application.services.PostService;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.usecases.PostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostUseCase postUseCase;
    private PostService postService;


    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id).orElseThrow();
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByAuthorId(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//    // Arkadaş postlarını tarih sırasına göre listeleme
//    @GetMapping("/friends")
//    public List<PostResponse> getFriendPostsByDate(@RequestParam User user,
//                                                   @RequestParam int page,
//                                                   @RequestParam int size) {
//        return postUseCase.getFriendPostsByDate(user, page, size);
//    }
}
