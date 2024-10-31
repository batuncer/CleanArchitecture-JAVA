package com.example.blog.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {

    private Long senderId;  // İsteği gönderen kullanıcının kimliği
    private Long receiverId; // İsteği alan kullanıcının kimliği
}
