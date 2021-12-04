package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.constant.Status;
import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.model.Users;
import com.java.spring.reddit.model.VerificationToken;
import com.java.spring.reddit.repository.UsersRepository;
import com.java.spring.reddit.repository.VerificationTokenRepository;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.MailService;
import com.java.spring.reddit.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(final RegisterRequest registerRequest) {
        userValidator.validateUserRequest(registerRequest);

        final Users users = new Users();
        users.setEmail(registerRequest.getEmail());
        users.setUserName(registerRequest.getUsername());
        users.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        users.setStatus(Status.CREATED);

        usersRepository.save(users);

        String token = generateVerificationToken(users);


    }

    private String generateVerificationToken(final Users users) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(users);

        verificationTokenRepository.save(verificationToken);

        return token;
    }
}
