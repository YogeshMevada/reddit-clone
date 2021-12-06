package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.SubRedditDto;

import java.util.List;

public interface SubRedditService {

    SubRedditDto save(SubRedditDto subRedditDto);

    List<SubRedditDto> getAll();
}
