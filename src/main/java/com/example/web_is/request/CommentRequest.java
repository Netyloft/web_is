package com.example.web_is.request;

import com.example.web_is.data.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CommentRequest {

    @Schema(description = "id статьи" , example = "1")
    private Long articleId;

    @Schema(description = "Текст", example = "Как живёт олег")
    private String text;

    @Schema(description = "id автора", example = "5")
    private Long authorId;
}
