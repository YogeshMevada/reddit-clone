package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.constant.Status;
import com.java.spring.reddit.model.Users;
import com.java.spring.reddit.service.UserService;
import jdk.nashorn.internal.runtime.options.Option;
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
import java.util.Optional;

import static com.java.spring.reddit.constant.Status.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<Users> userOptional = userService.findByUsername(username);
        final Users user = userOptional.orElseThrow(() -> new ValidationException("No User found"));
        return new User(user.getUsername(), user.getPassword(), ACTIVE.equals(user.getStatus()), true, true, true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
