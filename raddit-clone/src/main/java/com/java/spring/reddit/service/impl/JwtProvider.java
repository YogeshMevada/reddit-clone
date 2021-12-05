package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.exception.SystemException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;

@Slf4j
@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
        } catch (final KeyStoreException e) {
            log.error("Error occurred while loading keystore");
            throw new SystemException("Error occurred while loading keystore");
        }
    }

    public String generateToken(final Authentication authentication) {
        final User users = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(users.getUsername()).signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        return null;
    }
}
