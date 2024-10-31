package com.example.blog.application.services;

import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.PostRepository;
import com.example.blog.domain.usecases.CreatePostUseCase;
import com.example.blog.infrastructure.persistence.JpaPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {



        private final JpaPostRepository jpaPostRepository;
        private final CreatePostUseCase createPostUseCase;
        private final FriendshipService friendshipService;  // Injected
        private final PostRepository postRepository;        // Injected

        // Create a post
        public Post createPost(CreatePostRequest request) {
            return createPostUseCase.execute(request.getContent(), request.getUserId());
        }

        public Post savePost(Post post) {
            return postRepository.save(post);
        }

        // All posts
        public List<Post> getAllPosts() {
            return createPostUseCase.getPosts();
        }

        // Find by ID
        public Optional<Post> getPostById(Long postId) {
            return Optional.ofNullable(createPostUseCase.getPost(postId));
        }

        public List<Post> getPostsByAuthorId(Long authorId) {
            return createPostUseCase.getPostsByAuthorId(authorId);
        }

        public Post updatePost(Long postId, Post updatedPost) {
            Post existingPost = createPostUseCase.getPost(postId);
            if (existingPost == null) {
                throw new RuntimeException("Post not found");
            }

            existingPost.setContent(updatedPost.getContent());
            existingPost.setModifiedDate(LocalDateTime.now());
            return postRepository.save(existingPost);  // Use injected postRepository
        }

        // Delete post
        public void deleteById(Long postId) {
            Post post = createPostUseCase.getPost(postId);
            if (post == null) {
                throw new RuntimeException("Post not found");
            }
            postRepository.deletePost(post.getId());  // Use injected postRepository
        }

//    // Arkadaşların postlarını tarih sırasına göre getir
//    public List<Post> getFriendPostsByDate(User user, int page, int size) {
//        // Get friends of the user
//        List<User> friends = friendshipService.getFriends(user)
//                .stream()
//                .map(f -> f.getUserOne().equals(user) ? f.getUserTwo() : f.getUserOne())
//                .collect(Collectors.toList());
//
//        // Define the range of dates you want to retrieve posts for
//        LocalDate today = LocalDate.now();
//        List<LocalDate> dates = List.of(today.minusDays(7), today); // Example: Last 7 days
//
//        Pageable pageable = (Pageable) PageRequest.of(page, size);  // Create pageable
//        return jpaPostRepository.findFriendPostsByDate(dates, friends, pageable);
//    }
}
