package com.example.api.response.warning;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWarning {

    @Schema(description = "Общее сообщение с предупреждением", example = "Не найдено товаров: 102")
    private String title;

    @NotNull
    @Schema(description = "Детальный список предупреждений")
    private List<String> messages;

}
