package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String subRedditName;
    private String postName;
    private String url;
    private String description;
    private String username;
    private Integer voteCount = 0;
}
