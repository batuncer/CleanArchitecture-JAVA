package com.example.blog.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime date;
    private boolean deleted;
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

}
