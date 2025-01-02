package com.example.blog.application.services;

import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.User;

import com.example.blog.infrastructure.persistence.JpaFriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final JpaFriendshipRepository friendshipRepository;

    public void createFriendship(User userOne, User userTwo) {
        Friendship friendship1 = new Friendship();
        friendship1.setUser1(userOne);
        friendship1.setUser2(userTwo);
        friendship1.setCreatedAt(LocalDateTime.now());
        friendshipRepository.save(friendship1);

        Friendship friendship2 = new Friendship();
        friendship2.setUser1(userOne);
        friendship2.setUser2(userTwo);
        friendship2.setCreatedAt(LocalDateTime.now());
        friendshipRepository.save(friendship2);
    }

    public List<Friendship> getFriendships(User user) {
        List<Friendship> byUser1OrUser2 = friendshipRepository.findByUser1OrUser2(user, user);
        return byUser1OrUser2;
    }

}
