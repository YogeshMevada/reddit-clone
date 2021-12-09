package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.CommentRequest;
import com.java.spring.reddit.dto.CommentResponse;
import com.java.spring.reddit.entities.Comment;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment mapToComment(final CommentRequest commentRequest, final Post post, final Users users) {
        final Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setUser(users);
        return comment;
    }

    public CommentResponse mapToCommentResponse(final Comment comment) {
        final CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(comment.getId());
        commentResponse.setPostId(comment.getPost().getId());
        commentResponse.setText(comment.getText());
        commentResponse.setUsername(comment.getUser().getUsername());
        commentResponse.setCreatedDate(comment.getCreatedAt().toString());
        return commentResponse;
    }
}
