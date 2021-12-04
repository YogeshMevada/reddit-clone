package com.java.spring.reddit.exception;

import org.springframework.http.HttpStatus;

public class UserValidationException extends RuntimeException {

    public UserValidationException(final String message, final HttpStatus status) {
        super(message);
    }
}
