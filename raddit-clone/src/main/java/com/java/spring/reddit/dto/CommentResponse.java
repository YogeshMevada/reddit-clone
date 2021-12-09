package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long postId;
    private String text;
    private String username;
    private String createdDate;
}
