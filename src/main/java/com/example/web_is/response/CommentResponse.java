package com.example.web_is.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentResponse {
    @NotNull
    @Schema(description = "id", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "id статьи", example = "1")
    private Long articleId;

    @Schema(description = "Текст", example = "1")
    private String text;

    @Schema(description = "Автор коментария")
    private UserResponse author;
}
