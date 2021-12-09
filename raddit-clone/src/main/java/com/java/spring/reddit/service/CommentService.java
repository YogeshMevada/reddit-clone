package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.CommentRequest;
import com.java.spring.reddit.dto.CommentResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(CommentRequest commentRequest);

    List<CommentResponse> getCommentsByPostId(Long id);

    @Transactional(readOnly = true)
    List<CommentResponse> getCommentsByUser(String username);
}
