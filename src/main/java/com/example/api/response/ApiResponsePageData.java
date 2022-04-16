package com.example.api.response;

import com.example.api.resolver.paging.Pageable;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Страница элементов")
@JsonPropertyOrder({"startIndex", "totalItems", "currentItemCount", "items"})
public class ApiResponsePageData<T> extends ApiResponseListData<T> {

    @NotNull
    @Schema(description = "Индекс первого элемента страницы", example = "0")
    private Long startIndex;

    @NotNull
    @Schema(description = "Всего элементов в индексе", example = "20")
    private Long totalItems;

    @NotNull
    @Schema(description = "Количество элементов в текущей странице", example = "1")
    private Integer currentItemCount;

    public ApiResponsePageData(Pageable<T> page) {
        super(page.getItems());
        this.currentItemCount = page.getItems().size();
        this.startIndex = page.getStartIndex();
        this.totalItems = page.getTotalItems();
    }
}
