package com.java.spring.reddit.repository;

import com.java.spring.reddit.entities.Post;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, Users users);
}
