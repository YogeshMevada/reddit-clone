package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.AuthenticationRequest;
import com.java.spring.reddit.dto.AuthenticationResponse;
import com.java.spring.reddit.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    void verifyToken(String token);

    AuthenticationResponse login(AuthenticationRequest loginRequest);
}
