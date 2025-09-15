package com.socialdemo.socialdemo.service;

import com.socialdemo.socialdemo.dto.RegisterRequest;
import com.socialdemo.socialdemo.model.User;
import com.socialdemo.socialdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký user thường
    public User registerUser(RegisterRequest request) {
        // Kiểm tra trùng email trước khi tạo
        User existing = userRepository.findByEmail(request.getEmail());
        if (existing != null) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setProvider("local");
        user.setImageUrl(request.getImageUrl());
        return userRepository.save(user);
    }

    // Tìm hoặc tạo user từ OAuth2
    public User findOrCreateOAuthUser(String provider, String providerId, String email, String username, String imageUrl) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setRole("USER");
            user.setImageUrl(imageUrl);  // Lấy ảnh từ OAuth2
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}