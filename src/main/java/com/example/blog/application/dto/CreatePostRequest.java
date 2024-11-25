package com.example.blog.application.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePostRequest {

    private String authorName;
    private String content;
    private String picture;

}
