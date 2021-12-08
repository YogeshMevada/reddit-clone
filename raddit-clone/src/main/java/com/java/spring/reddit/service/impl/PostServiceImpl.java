package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.PostDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final SubRedditService subRedditService;

    private final AuthService authService;

    private final PostMapper postMapper;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public PostDto getPost(final Long id) {
        log.info("Get post by id {}.", id);
        return postMapper.mapToPostDto(postRepository.findById(id).orElseThrow(() -> new SystemException("Could not find post by id " + id)));
    }

    @Override
    @Transactional
    public PostDto createPost(final PostDto postDto) {
        log.info("Create post.");
        final SubReddit subReddit = subRedditService.findByName(postDto.getSubRedditName()).orElseThrow(() -> new SystemException("Could not find subreddit."));
        final Users users = authService.getCurrentUser();
        final Post post = postMapper.mapToPost(postDto);
        post.setSubReddit(subReddit);
        post.setUser(users);
        return null;
    }

    @Override
    public List<PostDto> findAllPosts() {
        log.info("Find all posts.");
        return postRepository.findAll().stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getPostsBySubReddit(final Long id) {
        log.info("Find all posts by subreddit id {}", id);
        final SubReddit subReddit = subRedditService.findById(id).orElseThrow(() -> new SystemException("Subreddit could not be found by id " + id));
        final List<Post> posts = postRepository.findBySubReddit(subReddit);
        return posts.stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByUsername(final String username) {
        log.info("Find all posts by username : ", username);
        final Users users = userService.findByUsername(username).orElseThrow(() -> new SystemException("User could not be found by name " + username));
        final List<Post> posts = postRepository.findByUser(users);
        return posts.stream().map(postMapper::mapToPostDto).collect(Collectors.toList());
    }
}
