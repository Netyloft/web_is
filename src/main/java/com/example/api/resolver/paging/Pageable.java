package com.example.api.resolver.paging;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Pageable<T> {

    private long startIndex;

    private long totalItems;

    private List<T> items;

}
