package com.java.spring.reddit.mapper;

import com.java.spring.reddit.dto.PostDto;
import com.java.spring.reddit.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto mapToPostDto(final Post post) {
        final PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setPostName(post.getPostName());
        postDto.setDescription(post.getDescription());
        postDto.setSubRedditName(post.getSubReddit().getName());
        return postDto;
    }

    public Post mapToPost(final PostDto postDto) {
        final Post post = new Post();
        post.setPostName(postDto.getPostName());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
