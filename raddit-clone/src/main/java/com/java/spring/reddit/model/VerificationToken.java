package com.java.spring.reddit.model;

import com.java.spring.reddit.constant.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token", schema = DatabaseConstants.SCHEMA)
public class VerificationToken extends EntityModel {

    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "expires_on")
    private Instant expiresOn;
}
