package com.example.blog.application.services;

import com.example.blog.domain.entities.BlockFriend;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.BlockFriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockFriendService {

    private final BlockFriendRepository blockFriendRepository;

    public BlockFriend blockUser(User blocker, User blocked)  {
        BlockFriend blockFriend = new BlockFriend();
        blockFriend.setBlocker(blocker);
        blockFriend.setBlocked(blocked);
        return blockFriendRepository.save(blockFriend);
    }

    public void unblockUser(User blocker, User blocked) {
        BlockFriend blockFriend = blockFriendRepository.findByBlockerAndBlocked(blocker, blocked);
        if (blockFriend != null) {
            blockFriendRepository.delete(blockFriend);
        }
    }

    public List<BlockFriend> getBlockFriends(User blocker) {
        return blockFriendRepository.findByBlocker(blocker);
    }

    public boolean isBlocked(Long blocker, Long blocked) {
        return blockFriendRepository.existsByBlockerAndBlocked(blocker, blocked);
    }
}
