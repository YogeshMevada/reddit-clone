package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.LoginRequest;
import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.exception.UserValidationException;
import com.java.spring.reddit.model.NotificationEmail;
import com.java.spring.reddit.model.Users;
import com.java.spring.reddit.model.VerificationToken;
import com.java.spring.reddit.repository.UsersRepository;
import com.java.spring.reddit.repository.VerificationTokenRepository;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.MailService;
import com.java.spring.reddit.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Optional;
import java.util.UUID;

import static com.java.spring.reddit.constant.Status.*;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void register(final RegisterRequest registerRequest) {
        log.info("User registration service started for : {}", registerRequest.getUsername());
        userValidator.validateUserRequest(registerRequest);

        final Users users = new Users();
        users.setEmail(registerRequest.getEmail());
        users.setUsername(registerRequest.getUsername());
        users.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        users.setStatus(CREATED);
        usersRepository.save(users);

        final String token = generateVerificationToken(users);
        mailService.sendMail(new NotificationEmail("Please Activate your Account.", users.getEmail(), "Thank you for Signing up to Reddit clone, " +
                "please click/open url to activate your account: http://localhost:8080/api/v1/auth/verification/" + token));
    }

    @Override
    @Transactional
    public void verifyToken(final String token) {
        final Optional<VerificationToken> tokens = verificationTokenRepository.findByToken(token);
        final VerificationToken verificationToken = tokens.orElseThrow(() -> new ValidationException("Token not found."));

        //TODO: validate token expiry

        final Users users = verificationToken.getUser();
        if (ACTIVE.equals(users.getStatus())) {
            throw new UserValidationException("User is already active & verified.");
        }
        users.setStatus(ACTIVE);
        usersRepository.save(users);
        log.info("User Activated successfully");

        mailService.sendMail(new NotificationEmail("User Account activated.", users.getEmail(), "Your account is activated successfully."));
    }

    @Override
    public void login(final LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    private String generateVerificationToken(final Users users) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(users);
        //TODO: set token expiry time for validation.
        verificationTokenRepository.save(verificationToken);
        log.info("Token generated: {}", token);
        return token;
    }
}
