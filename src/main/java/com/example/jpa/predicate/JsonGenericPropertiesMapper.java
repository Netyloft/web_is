package com.example.jpa.predicate;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class JsonGenericPropertiesMapper {

    public Map<String, List<String>> toJsonb(String jsonbArray) {
        return Arrays.stream(jsonbArray
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")
            .replace("'", "")
            .split(","))
            .map(i -> i.split(":"))
            .collect(Collectors.groupingBy(i -> i[0], Collectors.mapping(i -> i[1], Collectors.toList())));
    }

}
