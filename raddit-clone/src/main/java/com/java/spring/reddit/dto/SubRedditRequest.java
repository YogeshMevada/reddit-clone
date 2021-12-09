package com.java.spring.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditRequest {

    public Long id;

    @NotBlank(message = "SubReddit name can not be empty or null.")
    public String name;

    public String description;

    public Integer posts = 0;
}
