package com.example.blog.domain.usecases;

import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.application.dto.PostResponse;
import com.example.blog.application.services.PostService;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class PostUseCase {

    private PostService postService;

    //poST ATMA
    public PostResponse createPost(CreatePostRequest postRequest, User user) {
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setDate(LocalDateTime.now());
        post.setAuthor(user);

        if (postRequest.getPicture() != null) {
            post.setImage(postRequest.getPicture());
        }

        Post savedPost = postService.savePost(post);
        return new PostResponse(
                savedPost.getId(),
                savedPost.getContent(),
                savedPost.getImage(),
                savedPost.getDate(),
                savedPost.getAuthor().getId(),
                savedPost.getAuthor().getName(),
                savedPost.getAuthor().getProfilePicture()
        );
    }

//    // Tüm arkadaş postlarını tarih sırasına göre getir
//    public List<PostResponse> getFriendPostsByDate(User user, int page, int size) {
//        // Fetch posts with pagination
//        List<Post> posts = postService.getFriendPostsByDate(user, page, size);
//
//        // Her post için dönüşüm
//        return posts.stream().map(post -> new PostResponse(
//                post.getId(),
//                post.getContent(),
//                post.getImage(),
//                post.getDate(),
//                post.getAuthor().getId(),
//                post.getAuthor().getName(),
//                post.getAuthor().getProfilePicture()
//        )).collect(Collectors.toList());
//    }
}