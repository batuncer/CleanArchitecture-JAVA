package com.example.blog.infrastructure.persistence;

import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Status;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.FriendRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFriendRequestRepository extends JpaRepository<FriendRequest, Long> , FriendRequestRepository {

    List<FriendRequest> findByReceiverAndStatus(User receiver, Status status);


}
