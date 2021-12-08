package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditResponseDto {

    public List<SubRedditDto> subReddits;
    public Integer count;
}
