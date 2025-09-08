package com.viblo_example.entity;

import jakarta.persistence.*;   // to be able to use @Entity, @Table, other annotations and Generation.IDENTITY

// import javax.persistence.*; // for older versions (Java EE)
import java.util.List;

@Entity     // declare entity mapped to database table
@Table(name = "users")  // specify table name in database
public class User {
    @Id     // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment primary key
    private Long id;

    @Column(unique = true)      // column and constraints
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String imageUrl;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)  // relationship
    private List<Post> posts;



    public User() {}

    public User(String username, String email, String password, String imageUrl) {
        // auto generated id
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;

    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

}
