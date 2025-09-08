package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;  // Mã hóa bằng BCrypt

    private String provider;  // 'local', 'google', 'facebook'

    private String providerId;  // ID từ OAuth2

    @Column(nullable = false)
    private String role;  // 'USER', 'AUTHOR', 'ADMIN'

    private String imageUrl;  // URL ảnh đại diện
}