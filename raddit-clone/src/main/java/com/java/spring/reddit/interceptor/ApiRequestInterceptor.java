package com.java.spring.reddit.interceptor;

import com.java.spring.reddit.exception.AuthenticationException;
import com.java.spring.reddit.exception.UserValidationException;
import com.java.spring.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@AllArgsConstructor
public class ApiRequestInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        log.info("pre handle {}", request.getServletPath());
        authorize(request);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        log.info("post handle {}", request.getServletPath());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        log.info("after completion {}", request.getServletPath());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void authorize(final HttpServletRequest request) {
        try {
            final String token = getAuthToken(request);
            if (StringUtils.isNotBlank(token) && jwtProvider.validateToken(token)) {
                final String username = jwtProvider.getUsername(token);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (final Exception e) {
            log.error("Token validation failed", e);
            throw new AuthenticationException("Invalid token");
        }
    }

    private String getAuthToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new UserValidationException("User is unauthorized.");
    }
}
