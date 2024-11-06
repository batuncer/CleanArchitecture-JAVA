package com.example.blog.adapters.controllers;


import com.example.blog.application.services.BlockFriendService;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.BlockFriend;
import com.example.blog.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockFriendController {

    private final BlockFriendService blockFriendService;
    private final UserService userService;

    @PostMapping("/{blockedUserId}")
    public ResponseEntity<?> blockUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long blockedUserId) {
        User blocker = userService.findByUsername(userDetails.getUsername());
        User blocked = userService.findById(blockedUserId);
        blockFriendService.blockUser(blocker, blocked);
        return ResponseEntity.ok("User blocked successfully!");
    }

    public ResponseEntity<?> unblockUser(@AuthenticationPrincipal UserDetails userDetails, Long blockedUserId) {
        User blocker = userService.findByUsername(userDetails.getUsername());
        User blocked = userService.findById(blockedUserId);
        blockFriendService.unblockUser(blocker, blocked);
        return ResponseEntity.ok("User unblocked successfully!");
    }

    @GetMapping("/blocked")
    public ResponseEntity<List<BlockFriend>> getBlockedUsers(@AuthenticationPrincipal UserDetails userDetails) {
        User blocker = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(blockFriendService.getBlockFriends(blocker));
    }
}
