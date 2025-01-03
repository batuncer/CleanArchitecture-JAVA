package com.example.blog.application.services;

import com.example.blog.application.dto.UserResponse;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Bean olarak al
    private final RoleService roleService;

    // Constructor üzerinden inject edilen dependency
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Bean olarak alınacak
        this.roleService = roleService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void createUser( String username, String email, String country, String password, Set<String> roles) {
        User user = new User();
        String hashedPassword = passwordEncoder.encode(password);

        user.setUsername(username);
        user.setEmail(email);
        user.setCountry(country);

        user.setPassword(hashedPassword);

        userRepository.save(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // Email burada username yerine geçer
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    public User findById(Long receiverId) {
        return userRepository.findById(receiverId).orElse(null);
    }

   public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
   }

    public void setBigPicture(String username, String bigPictureUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setBigpicture(bigPictureUrl);
        userRepository.save(user);
    }

    public void setProfilePicture(String username, String profilePictureUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setProfilePicture(profilePictureUrl);
        userRepository.save(user);
    }

}
