package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email can not be empty or null")
    private String email;

    @NotBlank(message = "Username can not be empty or null")
    private String username;

    @NotBlank(message = "Password can not be empty or null")
    private String password;
}
