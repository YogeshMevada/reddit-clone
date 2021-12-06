package com.java.spring.reddit.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
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
