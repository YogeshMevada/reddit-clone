package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.PostRequest;
import com.java.spring.reddit.dto.PostResponse;
import com.java.spring.reddit.entities.*;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.PostMapper;
import com.java.spring.reddit.repository.CommentRepository;
import com.java.spring.reddit.repository.PostRepository;
import com.java.spring.reddit.repository.VoteRepository;
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

import static com.java.spring.reddit.entities.VoteType.DOWN;
import static com.java.spring.reddit.entities.VoteType.UP;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final VoteRepository voteRepository;

    private final SubRedditService subRedditService;

    private final AuthService authService;

    private final UserService userService;

    private final PostMapper postMapper;

    @Override
    public Post save(final Post post) {
        log.info("Save post.");
        return postRepository.save(post);
    }

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
        final Post savedPost = save(post);
        return postMapper.mapToPostDto(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> findAllPosts() {
        log.info("Find all posts.");
        return postRepository.findAll().stream().map(this::getPostResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> findAllTrendingPosts() {
        log.info("Find all trending posts.");
//        return postRepository.findAllTrending().stream().map(this::getPostResponse).collect(Collectors.toList());
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(final Long id) {
        log.info("Find all posts by subreddit id {}", id);
        final SubReddit subReddit = subRedditService.findById(id).orElseThrow(() -> new SystemException("Subreddit could not be found by id " + id));
        final List<Post> posts = postRepository.findBySubReddit(subReddit);
        return posts.stream().map(this::getPostResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(final String username) {
        log.info("Find all posts by username : ", username);
        final Users users = userService.findByUsername(username).orElseThrow(() -> new SystemException("User could not be found by name " + username));
        final List<Post> posts = postRepository.findByUser(users);
        return posts.stream().map(this::getPostResponse).collect(Collectors.toList());
    }

    private PostResponse getPostResponse(final Post post) {
        final PostResponse postResponse = postMapper.mapToPostDto(post);
        postResponse.setCommentCount(commentRepository.findByPost(post).size());
        postResponse.setUpVote(isPostUpVoted(post));
        postResponse.setDownVote(isPostDownVoted(post));
        return postResponse;
    }

    private boolean isPostUpVoted(final Post post) {
        return checkVoteType(post, UP);
    }

    private boolean isPostDownVoted(final Post post) {
        return checkVoteType(post, DOWN);
    }

    private boolean checkVoteType(final Post post, final VoteType voteType) {
        if (authService.isLoggedIn()) {
            final Optional<Vote> voteOptional = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
            return voteOptional.filter(vote -> voteType.equals(vote.getVoteType())).isPresent();
        }
        return false;
    }
}
