package com.example.blog.adapters.controllers;


import com.example.blog.application.dto.ApiResponse;
import com.example.blog.application.dto.FriendRequestDto;
import com.example.blog.application.dto.FriendRequestResponse;
import com.example.blog.application.mapper.FriendRequestMapper;
import com.example.blog.application.services.FriendRequestService;
import com.example.blog.application.services.FriendshipService;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/friend")
@CrossOrigin(origins= "http://localhost:3000")
public class FriendController {
    private final FriendRequestService friendRequestService;

    private final FriendshipService friendshipService;

    private final UserService userService;

    private final FriendRequestMapper friendRequestMapper;

    public FriendController(FriendRequestService friendRequestService, FriendshipService friendshipService, UserService userService, FriendRequestMapper friendRequestMapper) {
        this.friendRequestService = friendRequestService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.friendRequestMapper = friendRequestMapper;
    }

    @PostMapping("/request")
    public ResponseEntity<FriendRequest> sendFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody FriendRequestDto requestDto) {
        User sender = userService.findByUsername(userDetails.getUsername());
        User receiver = userService.findById(requestDto.getReceiverId());
        if (sender == receiver){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        FriendRequest request = friendRequestService.sendFriendRequest(sender.getId(), receiver.getId());
        return ResponseEntity.ok(request);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequests(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findByUsername(userDetails.getUsername());
        List<FriendRequest> friendRequests = friendRequestService.getPendingRequests(user);
        List<FriendRequestResponse> responses = friendRequests.stream()
                .map(friendRequestMapper::toFriendRequestResponse)
                .toList();
        return ResponseEntity.ok(responses);

    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<ApiResponse<FriendRequestResponse>> acceptRequest(@PathVariable Long id) {
        try {
            FriendRequest request = friendRequestService.acceptFriendRequest(id);
            FriendRequestResponse response = new FriendRequestResponse(
                    request.getId(),
                    request.getSender(),
                    request.getReceiver()
            );
            return ResponseEntity.ok(new ApiResponse<>("Friend request accepted", response, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, null, "Failed to accept friend request"));
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<FriendRequest> rejectRequest(@PathVariable Long id) {
        FriendRequest request = friendRequestService.rejectFriendRequest(id);
        return ResponseEntity.ok(request);
    }


    @GetMapping
    public ResponseEntity<List<Friendship>> getFriends(  @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Friendship> friendships = friendshipService.getFriendships(user);
        return ResponseEntity.ok(friendships);
    }


}
