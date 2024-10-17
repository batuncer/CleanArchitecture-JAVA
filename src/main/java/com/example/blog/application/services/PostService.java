package com.example.blog.application.services;

import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.usecases.CreatePostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private CreatePostUseCase createPostUseCase;

    // Create a post
    public Post createPost(CreatePostRequest request) {
        return createPostUseCase.execute(request.getContent(), request.getUserId());

    }

    // All posts
    public List<Post> getAllPosts() {
        return createPostUseCase.getPosts();
    }

    //find by id
    public Post getPostById(Long postId) {
        return createPostUseCase.getPost(postId);
    }

    public List<Post> getPostsByAuthorId(Long authorId) {
        return createPostUseCase.getPostsByAuthorId(authorId);
    }

    public Post updatePost(Long postId, Post updatedPost) {
        Post existingPost = createPostUseCase.getPost(postId);

        existingPost.setContent(updatedPost.getContent());

        return createPostUseCase.getPostRepository().save(existingPost);
    }

    // Delete post
    public void deleteById(Long postId) {
        Post post = createPostUseCase.getPost(postId);
        createPostUseCase.getPostRepository().deletePost(post.getId());
    }



}
