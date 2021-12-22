package com.java.spring.reddit.security;

import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.exception.SystemException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long tokenExpiration;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            final InputStream resourceAsStream = getClass().getResourceAsStream("/reddit-clone.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (final KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error("Error occurred while loading keystore", e);
            throw new SystemException("Error occurred while loading keystore");
        }
    }

    public Long getTokenExpiration() {
        return tokenExpiration;
    }

    public String generateToken(final Users users) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(users.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getPrivateKey())
                .compact();
    }

    public String generateToken(final User user) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getPrivateKey())
                .compact();
    }

    public boolean validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token);
        return true;
    }

    public String getUsername(final String token) {
        return Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("reddit-clone-sign", "secret".toCharArray());
        } catch (final KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            log.error("Could not load private key", e);
            throw new SystemException("Could not load private key");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("reddit-clone-sign").getPublicKey();
        } catch (final KeyStoreException e) {
            log.error("Could not load public key", e);
            throw new SystemException("Could not load public key");
        }
    }
}
