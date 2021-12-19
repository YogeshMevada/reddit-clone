package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.entities.Users;
import com.java.spring.reddit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;

import static com.java.spring.reddit.constant.Status.ACTIVE;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Users user = userService.findByUsername(username).orElseThrow(() -> new ValidationException("No User found"));
        switch (user.getStatus()) {
            case CREATED:
                throw new ValidationException("User is not verified. Please verify user.");
            case INACTIVE:
                throw new ValidationException("User is not active. Please contact admin.");
            case DEACTIVATED:
                throw new ValidationException("User is deactivated. Please contact admin.");
            case BLOCKED:
                throw new ValidationException("User is blocked. Please contact admin.");
        }
        return new User(user.getUsername(),
                user.getPassword(),
                ACTIVE.equals(user.getStatus()),
                true,
                true,
                true,
                getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
