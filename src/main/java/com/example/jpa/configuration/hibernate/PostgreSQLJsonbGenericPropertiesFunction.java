package com.example.jpa.configuration.hibernate;

import com.example.jpa.predicate.JsonGenericPropertiesMapper;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostgreSQLJsonbGenericPropertiesFunction implements SQLFunction {

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("The function must be passed 2 arguments");
        }

        String jsonbField = (String) arguments.get(0);
        String jsonArray = (String) arguments.get(1);

        Map<String, List<String>> collect = JsonGenericPropertiesMapper.toJsonb(jsonArray);
        Map<String, String> singleValueMap = new HashMap<>();
        Map<String, List<String>> multiValueMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : collect.entrySet()) {
            if (entry.getValue().size() == 1) {
                singleValueMap.put(entry.getKey(), entry.getValue().get(0));
            }
            if (entry.getValue().size() > 1) {
                multiValueMap.put(entry.getKey(), entry.getValue());
            }
        }

        String single = toSingle(jsonbField, singleValueMap);

        if (multiValueMap.size() > 0) {
            String multiple = toMultiple(jsonbField, multiValueMap);
            return "(%s and (%s))".formatted(single, multiple);
        } else {
            return single;
        }
    }

    private String toMultiple(String field, Map<String, List<String>> multipleMap) {
        return multipleMap.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                .map(value -> toSingle(field, Map.of(entry.getKey(), value))))
            .collect(Collectors.joining(" or "));
    }

    private String toSingle(String field, Map<String, String> singleMap) {
        String jsonValues = singleMap.entrySet().stream()
            .map(entry -> "{\"%s\":%s}".formatted(entry.getKey(), entry.getValue()))
            .collect(Collectors.joining(","));

        return "%s @> '[%s]'".formatted(field, jsonValues);
    }

    @Override
    public Type getReturnType(Type columnType, Mapping mapping) throws QueryException {
        return new BooleanType();
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

}
