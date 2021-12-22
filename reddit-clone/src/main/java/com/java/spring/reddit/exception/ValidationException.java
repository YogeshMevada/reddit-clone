package com.java.spring.reddit.exception;

import org.springframework.core.NestedRuntimeException;

public class ValidationException extends NestedRuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
