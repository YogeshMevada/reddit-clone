package com.java.spring.reddit.controller;

import com.java.spring.reddit.dto.RegisterRequest;
import com.java.spring.reddit.model.NotificationEmail;
import com.java.spring.reddit.service.AuthService;
import com.java.spring.reddit.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    private MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid final RegisterRequest registerRequest) {
        log.info("Registration started.");
        authService.register(registerRequest);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable @NotBlank final String token) {
        log.info("Registration started.");
        authService.verifyToken(token);
        return new ResponseEntity<>("User verification successful", HttpStatus.OK);
    }

    @PostMapping("/sendmail")
    public ResponseEntity<String> mail() {
        log.info("Send mail started.");
        mailService.sendMail(new NotificationEmail("Test mail", "info@reddit.clone.local", "Hi, This is test mail."));
        return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);
    }
}
