package com.example.blog.adapters.controllers;


import com.example.blog.application.dto.FriendRequestDto;
import com.example.blog.application.services.FriendRequestService;
import com.example.blog.application.services.FriendshipService;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.FriendRequest;
import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private FriendRequestService friendRequestService;

    private final FriendshipService friendshipService;

    private final UserService userService;

    @PostMapping("/request")
    public ResponseEntity<FriendRequest> sendFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody FriendRequestDto requestDto) {
        User sender = userService.findByUsername(userDetails.getUsername()); // Kullanıcı adından kullanıcıyı bul
        User receiver = userService.findById(requestDto.getReceiverId()); // Dışarıdan gelen receiver ID ile kullanıcıyı bul
        FriendRequest request = friendRequestService.sendFriendRequest(sender, receiver);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<FriendRequest> acceptRequest(@PathVariable Long id) {
        FriendRequest request = friendRequestService.acceptFriendRequest(id);
        friendshipService.createFriendship(request.getSender(), request.getReceiver());
        return ResponseEntity.ok(request);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<FriendRequest> rejectRequest(@PathVariable Long id) {
        FriendRequest request = friendRequestService.rejectFriendRequest(id);
        return ResponseEntity.ok(request);
    }
    @GetMapping("/pending")
    public ResponseEntity<List<FriendRequest>> getPendingRequests(@RequestParam Long userId) {
        User user = userService.findById(userId); // Kullanıcıyı bul
        List<FriendRequest> pendingRequests = friendRequestService.getPendingRequests(user);
        return ResponseEntity.ok(pendingRequests);
    }
    @GetMapping("/friends")
    public ResponseEntity<List<Friendship>> getFriends(@RequestParam Long userId) {

        User user = userService.findById(userId); // Kullanıcıyı bul
        List<Friendship> friendships = friendshipService.getFriendships(user);
        return ResponseEntity.ok(friendships);
    }

}
