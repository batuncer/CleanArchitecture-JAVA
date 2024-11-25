package com.example.blog.application.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String content;
    private String image;
    private LocalDateTime date;
    private String authorName;
    private String authorProfile;
    private int commentCount;
    private int likeCount;
}