package com.example.blog.infrastructure.persistence;


import com.example.blog.domain.entities.BlockFriend;
import com.example.blog.domain.interfaces.BlockFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBlockFriendRepository extends JpaRepository<BlockFriend, Long>, BlockFriendRepository {

}
