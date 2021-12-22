package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.AuthenticationRequest;
import com.java.spring.reddit.dto.AuthenticationResponse;
import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.entities.NotificationEmail;
import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.entities.VerificationToken;
import com.java.spring.reddit.exception.AuthenticationException;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.exception.UserValidationException;
import com.java.spring.reddit.repository.VerificationTokenRepository;
import com.java.spring.reddit.security.JwtProvider;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.MailService;
import com.java.spring.reddit.service.UserService;
import com.java.spring.reddit.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.UUID;

import static com.java.spring.reddit.constant.Status.ACTIVE;
import static com.java.spring.reddit.constant.Status.CREATED;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final VerificationTokenRepository verificationTokenRepository;

    private final UserService userService;

    private final MailService mailService;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

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
        userService.save(users);

        final String token = generateVerificationToken(users);
        mailService.sendMail(new NotificationEmail("Please Activate your Account.",
                users.getEmail(),
                "Thank you for Signing up to Reddit clone, " +
                        "please click/open url to activate your account: " +
                        "http://localhost:8080/api/v1/auth/verification/" + token));
    }

    @Override
    @Transactional
    public void verifyToken(final String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new ValidationException("Token not found."));
        final Users users = verificationToken.getUser();
        if (ACTIVE.equals(users.getStatus())) {
            throw new UserValidationException("User is already active & verified.");
        }
        //TODO: validate token expiry
        users.setStatus(ACTIVE);
        userService.save(users);
        log.info("User Activated successfully");

        mailService.sendMail(new NotificationEmail("User Account activated.",
                users.getEmail(),
                "Your account is activated successfully."));
    }

    @Override
    public AuthenticationResponse login(final AuthenticationRequest authenticationRequest) {
        try {
//            final Users users = userService.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new UserValidationException("User not found."));
//            if (!passwordEncoder.matches(authenticationRequest.getPassword(), users.getPassword())) {
//                throw new UserValidationException("Please enter correct Username and Password.");
//            }

            final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            final String token = jwtProvider.generateToken((User) authenticate.getPrincipal());
            return new AuthenticationResponse(authenticationRequest.getUsername(), token, jwtProvider.getTokenExpiration());
        } catch (final BadCredentialsException e) {
            throw new AuthenticationException("Username password is wrong.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Users getCurrentUser() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(user.getUsername()).orElseThrow(() -> new SystemException("User could not be found."));
    }

    @Override
    public boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Override
    public AuthenticationResponse logout() {
        return null;
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
