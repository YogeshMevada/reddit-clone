package com.java.spring.reddit.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private final HttpStatus status;

    private final String message;

    private final List<String> errors = new ArrayList<>();

    public ErrorResponse(final HttpStatus status, final String message, final List<String> errorList) {
        this.status = status;
        this.message = message;
        this.errors.addAll(errorList);
    }

    public ErrorResponse(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.errors.add(error);
    }

    public ErrorResponse(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
