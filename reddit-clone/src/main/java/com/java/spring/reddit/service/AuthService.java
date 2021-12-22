package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.AuthenticationRequest;
import com.java.spring.reddit.dto.AuthenticationResponse;
import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.entities.Users;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    void verifyToken(String token);

    AuthenticationResponse login(AuthenticationRequest loginRequest);

    Users getCurrentUser();

    boolean isLoggedIn();

    AuthenticationResponse logout();
}
