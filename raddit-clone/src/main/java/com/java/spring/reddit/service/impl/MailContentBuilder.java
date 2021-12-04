package com.java.spring.reddit.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(final String message) {
        final Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("registerMailTemplate", context);
    }
}
