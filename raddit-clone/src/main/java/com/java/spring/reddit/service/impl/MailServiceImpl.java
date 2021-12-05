package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.model.NotificationEmail;
import com.java.spring.reddit.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MailContentBuilder mailContentBuilder;

    @Override
    @Async
    public void sendMail(final NotificationEmail notificationEmail) {
        final MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("info@reddit.clone.local");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Registration mail sent for user : {}", notificationEmail.getRecipient());
        } catch (final MailException e) {
            log.error(e.getMessage(), e);
            throw new SystemException("Error occurred while sending registration mail to user.");
        }
    }
}
