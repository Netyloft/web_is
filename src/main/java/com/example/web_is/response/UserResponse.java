package com.example.web_is.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserResponse {
    @NotNull
    @Schema(description = "id", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Ник", example = "oleg")
    private String nickName;

    @Schema(description = "Пароль", example = "oleg123")
    private String password;

    @Schema(description = "Почта", example = "oleg123@oligi.oleg")
    private String email;
}
