package com.java.spring.reddit.entities;

import com.java.spring.reddit.constant.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_reddit", schema = DatabaseConstants.SCHEMA)
public class SubReddit extends EntityModel {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
