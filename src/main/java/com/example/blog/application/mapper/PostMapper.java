package com.example.blog.application.mapper;

import com.example.blog.application.dto.PostResponse;
import com.example.blog.domain.entities.Post;
import org.springframework.stereotype.Component;




@Component
public class PostMapper {

    public PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getImage(),
                post.getDate(),
                post.getAuthor().getName(),
                post.getAuthor().getProfilePicture(),
                post.getComments() == null ? 0 : post.getComments().size(),
                post.getLikes() != null ? post.getLikes().size() : 0
        );
    }
}