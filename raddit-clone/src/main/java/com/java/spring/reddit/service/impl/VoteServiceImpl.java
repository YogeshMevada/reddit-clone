package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.VoteRequest;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.entities.Vote;
import com.java.spring.reddit.entities.VoteType;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.repository.VoteRepository;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.PostService;
import com.java.spring.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final PostService postService;

    private final AuthService authService;

    @Override
    public void votePost(final VoteRequest voteRequest) {
        final Post post = postService.findById(voteRequest.getPostId()).orElseThrow(() -> new SystemException("Could not find post by id " + voteRequest.getPostId()));
        final Users users = authService.getCurrentUser();
        final Optional<Vote> voteOptional = voteRepository.findTopByPostAndUserOrderByIdDesc(post, users);
        if (voteOptional.isPresent()) {
            final Vote vote = voteOptional.get();
            if (vote.getVoteType().equals(voteRequest.getVoteType())) {
                log.info("Post vote({}) removed.", voteRequest.getVoteType());
                removeVote(post, voteRequest.getVoteType());
                voteRepository.delete(vote);
                return;
            } else {
                log.info("Post vote({}) changed.", voteRequest.getVoteType());
                changeVote(post, voteRequest.getVoteType());
                return;
            }
        }
        final Vote vote = new Vote();
        vote.setUser(authService.getCurrentUser());
        vote.setPost(post);
        vote.setVoteType(voteRequest.getVoteType());
        voteRepository.save(vote);
        log.info("Post vote({}) added.", voteRequest.getVoteType());
        addVote(post, voteRequest.getVoteType());
    }

    private void addVote(final Post post, final VoteType voteType) {
        if (VoteType.UP.equals(voteType)) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        postService.save(post);
    }

    private void changeVote(final Post post, final VoteType voteType) {
        if (VoteType.UP.equals(voteType)) {
            post.setVoteCount(post.getVoteCount() + 2);
        } else {
            post.setVoteCount(post.getVoteCount() - 2);
        }
        postService.save(post);
    }

    private void removeVote(final Post post, final VoteType voteType) {
        if (VoteType.UP.equals(voteType)) {
            post.setVoteCount(post.getVoteCount() - 1);
        } else {
            post.setVoteCount(post.getVoteCount() + 1);
        }
        postService.save(post);
    }
}
