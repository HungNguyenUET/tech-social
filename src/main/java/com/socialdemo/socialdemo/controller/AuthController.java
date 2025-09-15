package com.socialdemo.socialdemo.controller;

import com.socialdemo.socialdemo.dto.LoginRequest;
import com.socialdemo.socialdemo.dto.RegisterRequest;
import com.socialdemo.socialdemo.model.User;
import com.socialdemo.socialdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // Đăng ký user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    // Login thường
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // Có thể trả về JWT token ở đây nếu cần
        return ResponseEntity.ok("Login successful");
    }
}