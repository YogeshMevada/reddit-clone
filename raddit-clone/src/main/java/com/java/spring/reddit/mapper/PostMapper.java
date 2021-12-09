package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.PostRequest;
import com.java.spring.reddit.dto.PostResponse;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.entities.Users;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse mapToPostDto(final Post post) {
        final PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getId());
        postResponse.setPostName(post.getPostName());
        postResponse.setDescription(post.getDescription());
        postResponse.setUrl(post.getUrl());
        postResponse.setSubRedditName(post.getSubReddit().getName());
        postResponse.setUsername(post.getUser().getUsername());
        return postResponse;
    }

    public Post mapToPost(final PostRequest postDto, final SubReddit subReddit, final Users users) {
        final Post post = new Post();
        post.setPostName(postDto.getPostName());
        post.setDescription(postDto.getDescription());
        post.setUrl(postDto.getUrl());
        post.setSubReddit(subReddit);
        post.setUser(users);
        return post;
    }
}
