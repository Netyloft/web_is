package com.example.web_is.data;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private String text;
    private User author;
}
