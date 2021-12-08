package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.dto.SubRedditResponseDto;
import com.java.spring.reddit.entities.SubReddit;

import java.util.Optional;

public interface SubRedditService {

    SubRedditDto createSubReddit(SubRedditDto subRedditDto);

    SubRedditResponseDto findAll();

    Optional<SubReddit> findById(Long id);

    SubRedditDto getSubReddit(Long id);

    Optional<SubReddit> findByName(String subRedditName);
}
