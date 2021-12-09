package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.CommentRequest;
import com.java.spring.reddit.dto.CommentResponse;
import com.java.spring.reddit.entities.Comment;
import com.java.spring.reddit.entities.NotificationEmail;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.CommentMapper;
import com.java.spring.reddit.repository.CommentRepository;
import com.java.spring.reddit.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final AuthService authService;

    private final MailService mailService;

    private final UserService userService;

    private final MailContentBuilder mailContentBuilder;

    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public Comment save(final Comment comment) {
        log.info("Save comment.");
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public CommentResponse createComment(final CommentRequest commentRequest) {
        log.info("Create comment.");
        final Post post = postService.findById(commentRequest.getPostId()).orElseThrow(() -> new SystemException("Could not find post by id " + commentRequest.getPostId()));
        final Users users = authService.getCurrentUser();
        final Comment comment = commentMapper.mapToComment(commentRequest, post, users);
        final Comment savedComment = save(comment);
        if (!users.getUsername().equalsIgnoreCase(post.getUser().getUsername())) {
            final String message = mailContentBuilder.build(users.getUsername() + " posted a comment on your post.");
            mailService.sendMail(new NotificationEmail(users.getUsername() + " posted a comment on your post.", post.getUser().getEmail(), message));
        }
        return commentMapper.mapToCommentResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(final Long id) {
        log.info("Get comments by post id.");
        final Post post = postService.findById(id).orElseThrow(() -> new SystemException("Could not find post by id " + id));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToCommentResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByUser(final String username) {
        log.info("Get comments by post id.");
        final Users users = userService.findByUsername(username).orElseThrow(() -> new SystemException("User could not be found by name " + username));
        final List<Comment> comments = commentRepository.findByUser(users);
        return comments.stream().map(commentMapper::mapToCommentResponse).collect(Collectors.toList());
    }
}
