package com.example.blog.adapters.controllers;

import com.example.blog.application.services.LikeService;
import com.example.blog.domain.entities.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private LikeService likeService;

    @GetMapping("/likes")
    public List<Like> likes(){
        return likeService.findAll();
    }

    @PostMapping("/likes")
    public Like addLike(@RequestBody Like like){
       return likeService.save(like);
    }


}
