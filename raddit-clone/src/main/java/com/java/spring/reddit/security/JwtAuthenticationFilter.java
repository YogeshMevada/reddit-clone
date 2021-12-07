package com.java.spring.reddit.security;

import com.java.spring.reddit.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = getAuthToken(request);
            if (StringUtils.isNotBlank(token) && jwtProvider.validateToken(token)) {
                final String username = jwtProvider.getUsername(token);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (final Exception e) {
            log.error("Token validation failed", e);
            throw new AuthenticationException("Invalid token");
        }
        filterChain.doFilter(request, response);
    }

    private String getAuthToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
