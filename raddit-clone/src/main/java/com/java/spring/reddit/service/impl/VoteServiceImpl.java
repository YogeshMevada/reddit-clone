package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.VoteRequest;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.entities.Vote;
import com.java.spring.reddit.entities.VoteType;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.VoteMapper;
import com.java.spring.reddit.repository.VoteRepository;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.PostService;
import com.java.spring.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final PostService postService;

    private final AuthService authService;

    private final VoteMapper voteMapper;

    @Override
    public void votePost(final VoteRequest voteRequest) {
        final Post post = postService.findById(voteRequest.getPostId()).orElseThrow(() -> new SystemException("Could not find post by id " + voteRequest.getPostId()));
        final Users users = authService.getCurrentUser();
        final Optional<Vote> voteOptional = voteRepository.findTopByPostAndUserOrderByIdDesc(post, users);
        if (voteOptional.isPresent() && voteOptional.get().getVoteType().equals(voteRequest.getVoteType())) {
            throw new SystemException("You have already " + voteRequest.getVoteType() + " voted this post.");
        }
        if (VoteType.UP.equals(voteRequest.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.mapToVote(voteRequest, post, users));
        postService.save(post);
    }
}
