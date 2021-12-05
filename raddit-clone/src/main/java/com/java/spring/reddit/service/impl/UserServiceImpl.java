package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.model.Users;
import com.java.spring.reddit.repository.UsersRepository;
import com.java.spring.reddit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public Optional<Users> findByUsername(final String username) {
        return usersRepository.findByUsername(username);
    }
}
