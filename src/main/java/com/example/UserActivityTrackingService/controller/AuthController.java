package com.example.UserActivityTrackingService.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserActivityTrackingService.entity.User;
import com.example.UserActivityTrackingService.model.CreateUserRequest;
import com.example.UserActivityTrackingService.repository.UserRepository;
import com.example.UserActivityTrackingService.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Find user by username
        User dbUser = userRepo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());

        // Return success response with token
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "username", dbUser.getUsername(),
                "role", dbUser.getRole()
        ));
    }

    
    @PostMapping("/adduser")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest req) {

        // Check if username already exists
        if (userRepo.findByUsername(req.username).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists, please choose another");
        }

        if (!req.role.equals("ROLE_USER") && !req.role.equals("ROLE_ADMIN")) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid role");
        }

        User user = new User();
        user.setUsername(req.username);
        user.setPassword(req.password); // plain for now
        user.setRole(req.role);

        userRepo.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully");
    }
}

