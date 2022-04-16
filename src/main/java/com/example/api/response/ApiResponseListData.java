package com.example.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Список элементов")
public class ApiResponseListData<T> {

    private List<T> items;

    public ApiResponseListData(List<T> items) {
        this.items = items;
    }

}
