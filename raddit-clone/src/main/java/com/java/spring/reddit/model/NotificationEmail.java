package com.java.spring.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail extends EntityModel {

    private String subject;

    private String recipient;

    private String body;
}
