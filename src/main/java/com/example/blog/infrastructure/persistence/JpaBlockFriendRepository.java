package com.example.blog.infrastructure.persistence;


import com.example.blog.domain.entities.BlockFriend;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.BlockFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBlockFriendRepository extends JpaRepository<BlockFriend, Long>, BlockFriendRepository {

    @Override
    @Query("SELECT COUNT(b) > 0 FROM BlockFriend b WHERE b.blocker.id = :blockerId AND b.blocked.id = :blockedId")
    boolean existsByBlockerAndBlocked(@Param("blockerId") Long blockerId, @Param("blockedId") Long blockedId);

    @Override
    BlockFriend findByBlockerAndBlocked(User blocker, User blocked);

    @Override
    List<BlockFriend> findByBlocker(User blocker);
}
