package com.example.blog.application.services;


import com.example.blog.domain.entities.Like;
import com.example.blog.domain.interfaces.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private LikeRepository likeRepository;

    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public Like findById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    public List<Like> findAll() {
        return likeRepository.findAll();
    }


}
