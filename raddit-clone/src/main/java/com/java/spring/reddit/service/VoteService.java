package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.VoteRequest;

public interface VoteService {

    void votePost(VoteRequest voteRequest);
}
