package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditDto {

    public Long id;
    public String name;
    public String description;
    public Integer posts;
}
