package com.java.spring.reddit.dto;

import com.java.spring.reddit.entities.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    @NotNull
    private Long postId;

    @NotNull
    private VoteType voteType;
}
