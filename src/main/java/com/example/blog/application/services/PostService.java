package com.example.blog.application.services;


import com.example.blog.domain.entities.Post;

import com.example.blog.domain.interfaces.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

        @Autowired
        private final PostRepository postRepository;

        public PostService(PostRepository postRepository) {
            this.postRepository = postRepository;
        }

        public Post savePost(Post post) {
            return postRepository.save(post);
        }

        public Optional<Post> getPostById(Long id) {
            return postRepository.findById(id);
        }

        public List<Post> getPostsByAuthorId(Long userId) {
            return postRepository.findAllByAuthorId(userId);
        }

        public void deleteById(Long id) {
            postRepository.deleteById(id);
        }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }
}
//    // Arkadaşların postlarını tarih sırasına göre getir --  Blokladilarini gormemesi icin ekle
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

