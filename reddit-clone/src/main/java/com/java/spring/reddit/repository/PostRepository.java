package com.java.spring.reddit.repository;

import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubReddit(SubReddit subReddit);

    List<Post> findByUser(Users users);

//    @Query("Select p FROM Post p LEFT JOIN Comments WHERE ")
//    List<Post> findAllTrending();
}
