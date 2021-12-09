package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.repository.UsersRepository;
import com.java.spring.reddit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public Users save(final Users users) {
        log.info("Save user.");
        return usersRepository.save(users);
    }

    @Override
    public Optional<Users> findByUsername(final String username) {
        return usersRepository.findByUsername(username);
    }
}
