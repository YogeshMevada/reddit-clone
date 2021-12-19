package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.PostRequest;
import com.java.spring.reddit.dto.PostResponse;
import com.java.spring.reddit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid final PostRequest postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return new ResponseEntity<>(postService.findAllPosts(), OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<PostResponse>> getAllTrendingPosts() {
        return new ResponseEntity<>(postService.findAllTrendingPosts(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable @NotBlank final Long id) {
        return new ResponseEntity<>(postService.getPost(id), OK);
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubReddit(@PathVariable @NotBlank final Long id) {
        return new ResponseEntity<>(postService.getPostsBySubReddit(id), OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable @NotBlank(message = "Username can not be empty or null.") final String username) {
        return new ResponseEntity<>(postService.getPostsByUser(username), OK);
    }
}
