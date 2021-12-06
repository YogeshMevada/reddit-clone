package com.java.spring.reddit.validator;

import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.exception.UserValidationException;
import com.java.spring.reddit.model.Users;
import com.java.spring.reddit.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidator {

    private final UsersRepository usersRepository;

    public void validateUserRequest(final RegisterRequest registerRequest) {
        final Optional<Users> user = usersRepository.findByUsername(registerRequest.getUsername());
        if (user.isPresent()) {
            throw new UserValidationException("User already present.");
        }
    }
}
