package com.viblo_example.controller;

import com.viblo_example.entity.Post;
import com.viblo_example.entity.User;
import com.viblo_example.service.PostService;
import com.viblo_example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "home";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            post.setAuthor(userOptional.get());
            postService.save(post);
            return "redirect:/";
        }

        return "redirect:/login";
    }
}