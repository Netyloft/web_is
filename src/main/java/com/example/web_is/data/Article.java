package com.example.web_is.data;

import lombok.Data;

import java.util.List;

@Data
public class Article {

    private Long id;
    private String title;
    private String body;
    private User author;
    private String tags;
    private List<Comment> comments;
}
