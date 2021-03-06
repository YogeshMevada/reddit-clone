package com.java.spring.reddit.service;

import com.java.spring.reddit.entities.Users;

import java.util.Optional;

public interface UserService {

    Users save(Users users);

    Optional<Users> findByUsername(String username);
}
