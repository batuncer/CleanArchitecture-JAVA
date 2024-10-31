package com.example.blog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {


    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String country;
    private String city;
    private Set<String> roles;

}
