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

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        userService.createUser(request.getName(), request.getSurname(),request.getUsername(), request.getEmail(), request.getCountry(), request.getCity(), request.getPassword(), request.getRoles());
        return ResponseEntity.ok("User registered successfully!");
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

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}