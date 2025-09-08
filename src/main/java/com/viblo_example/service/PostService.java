package com.viblo_example.service;

import com.viblo_example.entity.Post;
import com.viblo_example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;  // what does this do
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }
}