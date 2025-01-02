package com.example.blog.adapters.controllers;
import com.example.blog.application.dto.AuthRequest;
import com.example.blog.application.dto.AuthResponse;
import com.example.blog.application.dto.SignupRequest;
import com.example.blog.application.services.UserService;
import com.example.blog.domain.entities.User;
import com.example.blog.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins= "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @GetMapping("/all")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        userService.createUser(request.getUsername(), request.getEmail(), request.getCountry(),  request.getPassword(), request.getRoles());
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtUtil.generateToken(request.getEmail());

            return new AuthResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        String username = userDetails.getUsername();
//        User user = userService.findByUsername(username);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    private final String UPLOAD_DIR = "src/main/resources/static/";
    private final String IMAGES_DIR = "images/";

    @PostMapping("/upload-big-picture")
    public ResponseEntity<String> uploadBigPicture(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestParam("file") MultipartFile file) {
        return uploadFile(userDetails, file, "bigPicture");
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam("file") MultipartFile file) {
        return uploadFile(userDetails, file, "profilePicture");
    }

    private ResponseEntity<String> uploadFile(UserDetails userDetails, MultipartFile file, String fileType) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String username = userDetails.getUsername();
            String fileName = file.getOriginalFilename();
            LocalDate currentDate = LocalDate.now();
            String year = String.valueOf(currentDate.getYear());
            String month = String.format("%02d", currentDate.getMonthValue());
            String day = String.format("%02d", currentDate.getDayOfMonth());

            Path directoryPath = Paths.get(UPLOAD_DIR, IMAGES_DIR, fileType, year, month, day);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path fullFilePath = directoryPath.resolve(fileName);
            Files.write(fullFilePath, file.getBytes());

            String fileUrl = IMAGES_DIR + fileType + "/" + year + "/" + month + "/" + day + "/" + fileName;

            if (fileType.equals("bigPicture")) {
                userService.setBigPicture(username, fileUrl);
            } else if (fileType.equals("profilePicture")) {
                userService.setProfilePicture(username, fileUrl);
            }

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed!");
        }
    }

    @GetMapping("userpage/{userId}")
    public ResponseEntity<User> getUserByUsername(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
}