package com.example.blog.domain.usecases;


import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.PostRepository;
import com.example.blog.domain.interfaces.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostUseCase {
    private PostRepository postRepository;
    private UserRepository userRepository;


    public Post execute( String content, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(user);
        post.setCreateTime(new Date());

        return postRepository.save(post);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public List<Post> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }
}
