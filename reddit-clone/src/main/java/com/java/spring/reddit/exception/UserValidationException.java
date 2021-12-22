package com.java.spring.reddit.exception;

import org.springframework.core.NestedRuntimeException;

public class UserValidationException extends NestedRuntimeException {

    public UserValidationException(final String message) {
        super(message);
    }
}
