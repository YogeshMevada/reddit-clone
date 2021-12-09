package com.java.spring.reddit.entities;

import com.java.spring.reddit.exception.SystemException;

import java.util.Arrays;

public enum VoteType {

    UP(1),
    DOWN(-1);

    private Integer direction;

    VoteType(Integer direction) {
    }

    public Integer getDirection() {
        return this.direction;
    }

    public static VoteType lookup(final Integer direction) {
        return Arrays.stream(VoteType.values()).filter(voteType -> voteType.getDirection().equals(direction)).findAny().orElseThrow(() -> new SystemException("Vote type invalid."));
    }
}
