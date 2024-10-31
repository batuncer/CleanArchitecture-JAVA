package com.example.blog.application.services;


import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Status;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendRequestService {

    private FriendRequestRepository friendRequestRepository;

    public FriendRequest sendFriendRequest(User sender, User receiver) {
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(Status.PENDING);
        request.setSentAt(LocalDateTime.now());
        return friendRequestRepository.save(request);
    }

    public FriendRequest acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(Status.ACCEPTED);
        return friendRequestRepository.save(request);
    }

    public FriendRequest rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(Status.REJECTED);
        return friendRequestRepository.save(request);
    }

    public List<FriendRequest> getPendingRequests(User user) {
        return friendRequestRepository.findByReceiverAndStatus(user, Status.PENDING);
    }
}
