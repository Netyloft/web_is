package com.example.api.response.warning;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponseWarningItem {

    @Schema(description = "Детальный текст предупреждения", example = "Не найдены коды товаров [1241, 1245]")
    private String message;

}
