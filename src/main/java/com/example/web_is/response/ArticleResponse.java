package com.example.web_is.response;

import com.example.web_is.data.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ArticleResponse {
    @NotNull
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "Заголовок", example = "Как живёт олег")
    private String title;

    @Schema(description = "Статья", example = "олег живёт плохо")
    private String body;

    @Schema(description = "Автор")
    private UserResponse author;

    @Schema(description = "Теги", example = "#жизнь_с_олегм")
    private String tags;

    @Schema(description = "Коментарии")
    private List<Comment> comments;
}
