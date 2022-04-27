package com.example.web_is.filter;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class ArticleFilter {
    private String title;
    private String tags;
    private Pageable pageable;
}
