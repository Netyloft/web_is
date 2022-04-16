package com.example.web_is.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ArticleRequest {

    @Schema(description = "id статьи", example = "4")
    private Long id;

    @Schema(description = "Заголовок", example = "Как живёт олег")
    private String title;

    @Schema(description = "Тело статьи", example = "олег не живёт")
    private String body;

    @Schema(description = "id автора", example = "3")
    private Long authorId;

    @Schema(description = "Теги", example = "#олег#неолег")
    private String tags;
}
