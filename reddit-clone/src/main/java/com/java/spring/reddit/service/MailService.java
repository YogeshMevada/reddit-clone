package com.java.spring.reddit.service;

import com.java.spring.reddit.entities.NotificationEmail;

public interface MailService {

    void sendMail(NotificationEmail notificationEmail);
}
