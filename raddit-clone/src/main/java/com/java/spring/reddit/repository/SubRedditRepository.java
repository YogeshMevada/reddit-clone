package com.java.spring.reddit.repository;

import com.java.spring.reddit.entities.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
}
