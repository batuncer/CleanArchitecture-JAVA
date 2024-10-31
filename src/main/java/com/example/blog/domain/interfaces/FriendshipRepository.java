package com.example.blog.domain.interfaces;


import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendshipRepository {

    List<Friendship> findByUser1OrUser2(User userOne, User userTwo);
    void save(Friendship friendship1);
}
