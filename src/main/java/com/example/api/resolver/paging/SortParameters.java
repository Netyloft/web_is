package com.example.api.resolver.paging;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SortParameters {

    private Map<String, String> fields;

}

