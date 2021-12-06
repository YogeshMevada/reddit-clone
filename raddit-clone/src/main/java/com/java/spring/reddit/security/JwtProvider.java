package com.java.spring.reddit.security;

import com.java.spring.reddit.exception.SystemException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
@Service
public class JwtProvider {

    private KeyStore keyStore;

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

    public String generateToken(final Authentication authentication) {
        final User users = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(users.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    public boolean validateToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJwt(token);
        return true;
    }

    public String getUsername(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
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
