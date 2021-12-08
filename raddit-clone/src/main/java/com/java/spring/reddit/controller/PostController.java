package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.PostDto;
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

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid final PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(postService.findAllPosts(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable @NotBlank final Long id) {
        return new ResponseEntity<>(postService.getPost(id), OK);
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostDto>> getPostsBySubReddit(@PathVariable @NotBlank final Long id) {
        return new ResponseEntity<>(postService.getPostsBySubReddit(id), OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable @NotBlank final String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), OK);
    }
}
