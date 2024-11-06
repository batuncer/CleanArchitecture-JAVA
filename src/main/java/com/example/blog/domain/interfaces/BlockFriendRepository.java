package com.example.blog.domain.interfaces;

import com.example.blog.domain.entities.BlockFriend;
import com.example.blog.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface BlockFriendRepository {

    boolean existsByBlockerAndBlocked(User blocker, User blocked);
    BlockFriend findByBlockerAndBlocked(User blocker, User blocked);
    List<BlockFriend> findByBlocker(User blocker);
    BlockFriend save(BlockFriend blockFriend);
    void delete(BlockFriend blockFriend);
}
