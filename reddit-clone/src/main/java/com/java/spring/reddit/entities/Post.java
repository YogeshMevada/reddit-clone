package com.java.spring.reddit.entities;

import com.java.spring.reddit.constant.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post", schema = DatabaseConstants.SCHEMA)
public class Post extends EntityModel {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    private SubReddit subReddit;

    @NotBlank(message = "Post name can not be empty or null")
    @Column(name = "post_name")
    private String postName;

    @Nullable
    @Column(name = "url")
    private String url;

    @Nullable
    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "vote_count")
    private Integer voteCount = 0;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
