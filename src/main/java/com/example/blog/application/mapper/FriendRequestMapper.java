package com.example.blog.application.mapper;


import com.example.blog.application.dto.FriendRequestResponse;
import com.example.blog.domain.entities.FriendRequest;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestMapper {

    public FriendRequestResponse toFriendRequestResponse(FriendRequest friendRequest) {
        return new FriendRequestResponse(
                friendRequest.getId(),
                friendRequest.getSender(),
                friendRequest.getReceiver()
        );
    }
}