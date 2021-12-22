package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.PostRequest;
import com.java.spring.reddit.dto.PostResponse;
import com.java.spring.reddit.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post save(Post post);

    Optional<Post> findById(Long id);

    PostResponse getPost(Long id);

    PostResponse createPost(PostRequest postDto);

    List<PostResponse> findAllPosts();

    List<PostResponse> findAllTrendingPosts();

    List<PostResponse> getPostsBySubReddit(Long id);

    List<PostResponse> getPostsByUser(String name);
}
