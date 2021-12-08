package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.entities.SubReddit;
import org.springframework.stereotype.Component;

@Component
public class SubRedditMapper {

    public SubReddit mapToSubReddit(final SubRedditDto subRedditDto) {
        final SubReddit subReddit = new SubReddit();
        subReddit.setName(subRedditDto.getName());
        subReddit.setDescription(subRedditDto.getDescription());
        return subReddit;
    }

    public SubRedditDto mapToSubRedditDto(final SubReddit subReddit) {
        final SubRedditDto subRedditRequestDto = new SubRedditDto();
        subRedditRequestDto.setId(subReddit.getId());
        subRedditRequestDto.setName(subReddit.getName());
        subRedditRequestDto.setDescription(subReddit.getDescription());
        return subRedditRequestDto;
    }
}
