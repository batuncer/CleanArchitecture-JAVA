package com.example.blog.application.services;


import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Status;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final BlockFriendService blockFriendService;
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final FriendshipService friendshipService;


    public FriendRequest sendFriendRequest(Long senderId, Long receiverId) {

        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        if(blockFriendService.isBlocked(senderId, receiverId) || blockFriendService.isBlocked(receiverId, senderId)) {
            throw new RuntimeException("Cannot send friend request - user is blocked.");

        }
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
        friendRequestRepository.save(request);
        friendshipService.createFriendship(request.getSender(), request.getReceiver());

        return request;
    }

    public FriendRequest rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(Status.REJECTED);
        return friendRequestRepository.save(request);
    }

    public List<FriendRequest> getPendingRequests(User user) {
        return friendRequestRepository.findByReceiverAndStatus(user, Status.PENDING);
    }

    public List<FriendRequest> getAcceptedRequests(User user) {
        return friendRequestRepository.findByReceiverAndStatus(user, Status.ACCEPTED);
    }
}
