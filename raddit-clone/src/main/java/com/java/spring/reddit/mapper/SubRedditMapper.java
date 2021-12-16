package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.SubRedditRequest;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.entities.Users;
import org.springframework.stereotype.Component;

@Component
public class SubRedditMapper {

    public SubReddit mapToSubReddit(final SubRedditRequest subRedditDto, final Users users) {
        final SubReddit subReddit = new SubReddit();
        subReddit.setName(subRedditDto.getName());
        subReddit.setDescription(subRedditDto.getDescription());
        subReddit.setUser(users);
        return subReddit;
    }

    public SubRedditRequest mapToSubRedditDto(final SubReddit subReddit) {
        final SubRedditRequest subRedditRequestDto = new SubRedditRequest();
        subRedditRequestDto.setId(subReddit.getId());
        subRedditRequestDto.setName("r/".concat(subReddit.getName()));
        subRedditRequestDto.setDescription(subReddit.getDescription());
        return subRedditRequestDto;
    }
}
