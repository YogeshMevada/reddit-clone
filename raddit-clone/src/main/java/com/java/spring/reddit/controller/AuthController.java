package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.service.AuthService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public String register(final RegisterRequest registerRequest) {
        log.info("Registration started.");
        authService.register(registerRequest);
        return "<H1>Registration Started!</H1>";
    }
}
