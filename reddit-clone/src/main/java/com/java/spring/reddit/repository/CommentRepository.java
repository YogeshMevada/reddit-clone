package com.java.spring.reddit.repository;

import com.java.spring.reddit.entities.Comment;
import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(Users users);
}
