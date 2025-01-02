package com.example.blog.adapters.controllers;
import com.example.blog.application.dto.CreatePostRequest;
import com.example.blog.application.dto.PostResponse;
import com.example.blog.application.mapper.PostMapper;
import com.example.blog.application.services.PostService;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@CrossOrigin(origins= "http://localhost:3000")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostController(PostService postService, UserService userService, PostMapper postMapper) {
        this.postService = postService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getImage());
        post.setAuthor(user);
        post.setDate(LocalDateTime.now());
        Post savedPost = postService.savePost(post);
        PostResponse response = postMapper.toPostResponse(savedPost);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userposts")
    public ResponseEntity<List<PostResponse>> getUserPostsByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        List<Post> posts = postService.getPostsByAuthorId(user.getId());
        List<PostResponse> responses = posts.stream()
                .map(postMapper::toPostResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(postMapper.toPostResponse(post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByAuthorId(userId);
        List<PostResponse> responses = posts.stream()
                .map(postMapper::toPostResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
            List<Post> posts = (List<Post>) postService.getPosts();
            List<PostResponse> responses = posts.stream()
                    .map(postMapper::toPostResponse)
                    .toList();
            return ResponseEntity.ok(responses);
        }

    private final String UPLOAD_DIR = "src/main/resources/static/";
    private final String IMAGES_DIR = "images/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath =  file.getOriginalFilename();
            LocalDate currentDate = LocalDate.now();
            String year = String.valueOf(currentDate.getYear());
            String month = String.format("%02d", currentDate.getMonthValue());
            String day = String.format("%02d", currentDate.getDayOfMonth());

            Path directoryPath = Paths.get(UPLOAD_DIR, IMAGES_DIR, year, month, day);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path fullFilePath = directoryPath.resolve(filePath);
            Files.write(fullFilePath, file.getBytes());

            String fileUrl =  IMAGES_DIR + year + "/" + month + "/" + day + "/" + filePath;
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yükleme başarısız!");
        }
    }

//    private String getFilePath(String BaseDir){
//        LocalDate currentDate = LocalDate.now();
//        String year = String.valueOf(currentDate.getYear());
//        String month = String.format("%02d", currentDate.getMonthValue());
//        String day = String.format("%02d", currentDate.getDayOfMonth());
//
//        Path baseDirectory = Paths.get(UPLOAD_DIR , BaseDir, year, month, day);
//
//        try {
//            if (!Files.exists(baseDirectory)) {
//                Files.createDirectories(baseDirectory); // Eksik ara klasörleri de oluşturur
//                System.out.println("Klasör yapısı oluşturuldu: " + baseDirectory.toAbsolutePath());
//            } else {
//                System.out.println("Klasör zaten mevcut: " + baseDirectory.toAbsolutePath());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  baseDirectory.toString();
//
//    }

}
