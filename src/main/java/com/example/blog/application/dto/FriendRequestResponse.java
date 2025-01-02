package com.example.blog.application.dto;

import com.example.blog.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestResponse {
    private Long id;
    private User sender;
    private User receiver;
}