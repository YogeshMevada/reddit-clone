package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private final String username;
    private final String token;
    private final Long expiresAt;
}
