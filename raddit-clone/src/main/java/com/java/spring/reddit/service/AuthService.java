package com.java.spring.reddit.service;

import com.java.spring.reddit.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
}
