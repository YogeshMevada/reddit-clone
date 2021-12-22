package com.java.spring.reddit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/home")
    public String home() {
        log.info("Home controller reached.");
        return "<H1>Welcome Home!</H1>";
    }

    @GetMapping("/index")
    public String index() {
        log.info("Index controller reached.");
        return "<H1>Welcome Index!</H1>";
    }
}
