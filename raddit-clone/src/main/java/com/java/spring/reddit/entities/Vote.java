package com.java.spring.reddit.entities;

import com.java.spring.reddit.constant.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vote", schema = DatabaseConstants.SCHEMA)
public class Vote extends EntityModel {

    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
