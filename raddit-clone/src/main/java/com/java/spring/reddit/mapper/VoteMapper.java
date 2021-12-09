package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.VoteRequest;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.entities.Vote;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    public Vote mapToVote(final VoteRequest voteRequest, final Post post, final Users users) {
        final Vote vote = new Vote();
        vote.setVoteType(voteRequest.getVoteType());
        vote.setPost(post);
        vote.setUser(users);
        return vote;
    }
}
