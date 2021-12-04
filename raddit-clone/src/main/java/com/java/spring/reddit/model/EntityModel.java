package com.java.spring.reddit.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class EntityModel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Instant updatedAt;
}
