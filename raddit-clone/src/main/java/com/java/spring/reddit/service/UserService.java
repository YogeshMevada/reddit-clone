package com.java.spring.reddit.service;

import com.java.spring.reddit.model.Users;

import java.util.Optional;

public interface UserService {

    Optional<Users> findByUsername(String username);
}
