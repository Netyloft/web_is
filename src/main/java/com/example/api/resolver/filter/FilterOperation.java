package com.example.api.resolver.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FilterOperation {

    private FilterOperatorType type = FilterOperatorType.IN;

    private boolean manual = false;

    private Integer groupId;

    private FilterValueType filterValueType;

    private List<?> values = new ArrayList<>();

}

