package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto getPost(Long id);

    PostDto createPost(PostDto postDto);

    List<PostDto> findAllPosts();

    List<PostDto> getPostsBySubReddit(Long id);

    List<PostDto> getPostsByUsername(String name);
}
