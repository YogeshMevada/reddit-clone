package com.java.spring.reddit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        new ErrorResponse(HttpStatus.FORBIDDEN, authException.getMessage(), authException.getLocalizedMessage());
//        final Map<String, Object> errorMap = new HashMap<>();
//        errorMap.put("status", HttpStatus.FORBIDDEN.value());
//        errorMap.put("message", authException.getMessage());
//        final List<String> errors = new ArrayList<>();
//        errors.add(authException.getLocalizedMessage());
//        errorMap.put("errors", errors);
//        final JSONObject jsonObject = new JSONObject();
//        jsonObject.putAll(errorMap);
//        response.getWriter().write(jsonObject.toString());
        log.error(">>>>>>>>> Authentication Failed from Authentication Entry Point.");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
