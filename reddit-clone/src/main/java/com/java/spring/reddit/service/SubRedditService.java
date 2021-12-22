package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.SubRedditRequest;
import com.java.spring.reddit.dto.SubRedditResponse;
import com.java.spring.reddit.entities.SubReddit;

import java.util.Optional;

public interface SubRedditService {

    SubReddit save(SubReddit subReddit);

    SubRedditRequest createSubReddit(SubRedditRequest subRedditDto);

    SubRedditResponse findAll();

    Optional<SubReddit> findById(Long id);

    SubRedditRequest getSubReddit(Long id);

    Optional<SubReddit> findByName(String subRedditName);
}
