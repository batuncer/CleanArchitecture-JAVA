package com.example.blog.infrastructure.persistence;


import com.example.blog.domain.entities.Friendship;
import com.example.blog.domain.interfaces.FriendshipRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface JpaFriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepository {


}
