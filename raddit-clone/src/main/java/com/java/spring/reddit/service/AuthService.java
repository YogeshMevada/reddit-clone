package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.LoginRequest;
import com.java.spring.reddit.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    void verifyToken(String token);

    void login(LoginRequest loginRequest);
}
