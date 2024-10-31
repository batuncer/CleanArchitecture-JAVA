package com.example.blog.infrastructure.persistence;


import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface JpaFriendshipRepository extends JpaRepository<Friendship, Long> {


}
