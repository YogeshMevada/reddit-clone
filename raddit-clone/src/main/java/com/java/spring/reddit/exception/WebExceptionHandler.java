package com.java.spring.reddit.exception;

import com.java.spring.reddit.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class WebExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleException(final MethodArgumentNotValidException exception) {
        log.error("Validation failed!", exception);
        final List<String> errorList = exception.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ErrorResponse(BAD_REQUEST, "Request validation failed", errorList);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(UserValidationException.class)
    public ErrorResponse handleException(final UserValidationException exception) {
        log.error("User validation failed.", exception);
        return new ErrorResponse(BAD_REQUEST, exception.getMessage(), exception.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleException(final ValidationException exception) {
        log.error("Validation failed.", exception);
        return new ErrorResponse(BAD_REQUEST, exception.getMessage(), exception.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleException(final AuthenticationException exception) {
        log.error("Exception occurred", exception);
        return new ErrorResponse(UNAUTHORIZED, exception.getMessage(), exception.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SystemException.class)
    public ErrorResponse handleException(final SystemException exception) {
        log.error("Exception occurred", exception);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(final Exception exception) {
        log.error("Exception occurred", exception);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getLocalizedMessage());
    }
}
