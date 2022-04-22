package com.example.web_is.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ArticleFilter {
    @Schema(description = "Заголовок", example = "Как живёт олег")
    private String title;

    @Schema(description = "Теги", example = "#олег#неолег")
    private String tags;
}
