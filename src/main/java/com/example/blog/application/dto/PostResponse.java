package com.example.blog.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String content;
    private String image;
    private LocalDateTime date;
    private Long authorId;
    private String authorName;
    private String authorProfile;
}
