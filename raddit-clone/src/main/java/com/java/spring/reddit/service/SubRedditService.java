package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.dto.SubRedditResponseDto;

public interface SubRedditService {

    SubRedditDto save(SubRedditDto subRedditDto);

    SubRedditResponseDto getAll();
}
