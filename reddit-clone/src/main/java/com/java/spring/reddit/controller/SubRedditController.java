package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.SubRedditRequest;
import com.java.spring.reddit.dto.SubRedditResponse;
import com.java.spring.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subreddit")
public class SubRedditController {

    private final SubRedditService subRedditService;

    @PostMapping
    public ResponseEntity<SubRedditRequest> createSubReddit(@RequestBody @Valid final SubRedditRequest subRedditDto) {
        log.info("Create subreddit");
        return new ResponseEntity<>(subRedditService.createSubReddit(subRedditDto), CREATED);
    }

    @GetMapping
    public ResponseEntity<SubRedditResponse> getSubReddits() {
        log.info("Get subreddits");
        return new ResponseEntity<>(subRedditService.findAll(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubRedditRequest> getSubReddit(@PathVariable @NotBlank Long id) {
        log.info("Get subreddits");
        return new ResponseEntity<>(subRedditService.getSubReddit(id), OK);
    }
}
