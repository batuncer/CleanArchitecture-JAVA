package com.example.blog.application.dto;

import com.example.blog.domain.entities.User;
import jakarta.annotation.Nullable;
import lombok.*;


@Getter
@Setter
public class CreatePostRequest {

    private String content;
    @Nullable
    private String picture;



}
