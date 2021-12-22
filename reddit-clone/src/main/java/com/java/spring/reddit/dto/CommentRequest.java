package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private Long commentId;
    private Long postId;
    @NotBlank(message = "Comment text can not be empty or null.")
    private String text;
    private String username;
}
