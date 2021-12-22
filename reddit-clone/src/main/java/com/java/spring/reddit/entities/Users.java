package com.java.spring.reddit.entities;

import com.java.spring.reddit.constant.DatabaseConstants;
import com.java.spring.reddit.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = DatabaseConstants.SCHEMA)
public class Users extends EntityModel {

    @NotEmpty(message = "User name is required")
    @Column(name = "user_name")
    private String username;

    @NotEmpty(message = "Email is required")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password is required")
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
