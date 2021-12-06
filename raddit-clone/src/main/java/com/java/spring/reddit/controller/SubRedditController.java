package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subreddit")
public class SubRedditController {

    private final SubRedditService subRedditService;

    @PostMapping("")
    public ResponseEntity<SubRedditDto> createSubReddit(@RequestBody @Valid final SubRedditDto subRedditDto) {
        log.info("Create subreddit");
        return new ResponseEntity<>(subRedditService.save(subRedditDto), CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<SubRedditDto>> getSubReddits() {
        log.info("Get subreddits");
        return new ResponseEntity<>(subRedditService.getAll(), OK);
    }
}
