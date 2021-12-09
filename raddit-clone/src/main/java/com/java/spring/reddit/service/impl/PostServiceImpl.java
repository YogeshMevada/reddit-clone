package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.PostRequest;
import com.java.spring.reddit.dto.PostResponse;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.PostMapper;
import com.java.spring.reddit.repository.PostRepository;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.PostService;
import com.java.spring.reddit.service.SubRedditService;
import com.java.spring.reddit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final SubRedditService subRedditService;

    private final AuthService authService;

    private final UserService userService;

    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findById(final Long id) {
        log.info("Find post by id {}.", id);
        return postRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(final Long id) {
        log.info("Get post by id {}.", id);
        final Post post = postRepository.findById(id).orElseThrow(() -> new SystemException("Could not find post by id " + id));
        return postMapper.mapToPostDto(post);
    }

    @Override
    @Transactional
    public PostResponse createPost(final PostRequest postRequest) {
        log.info("Create post.");
        final SubReddit subReddit = subRedditService.findByName(postRequest.getSubRedditName()).orElseThrow(() -> new SystemException("Could not find subreddit."));
        final Users users = authService.getCurrentUser();
        final Post post = postMapper.mapToPost(postRequest, subReddit, users);
        final Post savedPost = postRepository.save(post);
        return postMapper.mapToPostDto(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> findAllPosts() {
        log.info("Find all posts.");
        return postRepository.findAll().stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(final Long id) {
        log.info("Find all posts by subreddit id {}", id);
        final SubReddit subReddit = subRedditService.findById(id).orElseThrow(() -> new SystemException("Subreddit could not be found by id " + id));
        final List<Post> posts = postRepository.findBySubReddit(subReddit);
        return posts.stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(final String username) {
        log.info("Find all posts by username : ", username);
        final Users users = userService.findByUsername(username).orElseThrow(() -> new SystemException("User could not be found by name " + username));
        final List<Post> posts = postRepository.findByUser(users);
        return posts.stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }
}
