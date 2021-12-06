package com.java.spring.reddit.entities;

import com.java.spring.reddit.constant.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment", schema = DatabaseConstants.SCHEMA)
public class Comment extends EntityModel {

    @NotEmpty
    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = LAZY)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
