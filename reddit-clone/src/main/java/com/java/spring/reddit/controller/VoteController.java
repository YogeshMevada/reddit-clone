package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.VoteRequest;
import com.java.spring.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> votePost(@RequestBody @Valid final VoteRequest voteRequest) {
        voteService.votePost(voteRequest);
        return new ResponseEntity<>(OK);
    }
}
