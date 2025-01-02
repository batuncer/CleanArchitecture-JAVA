package com.example.blog.domain.interfaces;



import com.example.blog.application.dto.FriendRequestResponse;
import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.Status;
import com.example.blog.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository {

    List<FriendRequest> findByReceiverAndStatus(User receiver, Status status);


    FriendRequest save(FriendRequest request);

    Optional<FriendRequest> findById(Long requestId);
}
