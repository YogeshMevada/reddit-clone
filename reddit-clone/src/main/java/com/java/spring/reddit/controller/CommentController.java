package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.CommentRequest;
import com.java.spring.reddit.dto.CommentResponse;
import com.java.spring.reddit.service.CommentService;
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
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid final CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.createComment(commentRequest), CREATED);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable @NotBlank(message = "Post id can not be empty or null.") final Long id) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(id), OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable @NotBlank(message = "Username can not be empty or null.") final String username) {
        return new ResponseEntity<>(commentService.getCommentsByUser(username), OK);
    }
}
