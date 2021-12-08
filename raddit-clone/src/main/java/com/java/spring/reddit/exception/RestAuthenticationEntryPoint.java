package com.java.spring.reddit.exception;

import com.java.spring.reddit.dto.ErrorResponse;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        new ErrorResponse(HttpStatus.FORBIDDEN, authException.getMessage(), authException.getLocalizedMessage());
        final Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", HttpStatus.FORBIDDEN.value());
        errorMap.put("message", authException.getMessage());
        final List<String> errors = new ArrayList<>();
        errors.add(authException.getLocalizedMessage());
        errorMap.put("errors", errors);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(errorMap);
        response.getWriter().write(jsonObject.toString());
    }
}
