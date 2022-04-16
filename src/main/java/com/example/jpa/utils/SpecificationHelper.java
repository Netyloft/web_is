package com.example.jpa.utils;

import com.example.api.resolver.filter.FilterOperation;
import com.example.api.resolver.filter.FilterParameters;
import com.example.api.resolver.filter.FilterValueType;
import com.example.jpa.configuration.hibernate.PostgreSQLPromoDialect;
import com.example.jpa.predicate.JsonContainsPredicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpecificationHelper {

    private static final String JSON_ARRAY_START_TOKEN = "'[";
    private static final String JSON_ARRAY_END_TOKEN = "]'";

    public static <E> Specification<E> toSpecification(FilterParameters parameters) {
        return (root, query, builder) -> {
            Predicate[] predicates = parameters.getFilters().entrySet().stream()
                .filter(entry -> !entry.getValue().isManual())
                .filter(entry -> entry.getValue().getGroupId() == null)
                .map(entry -> {
                    FilterOperation operation = entry.getValue();
                    String filter = entry.getKey();
                    return getPredicate(operation, filter, root, builder);
                })
                .toArray(Predicate[]::new);

            Predicate[] groupedPredicates = parameters.getFilters().entrySet().stream()
                .filter(entry -> !entry.getValue().isManual())
                .filter(entry -> entry.getValue().getGroupId() != null)
                .collect(Collectors.groupingBy(entry -> entry.getValue().getGroupId()))
                .values()
                .stream()
                .map(entries -> {
                    Predicate[] groupedPredicate = new Predicate[entries.size()];
                    for (int i = 0; i < entries.size(); i++) {
                        Map.Entry<String, FilterOperation> entry = entries.get(i);
                        FilterOperation operation = entry.getValue();
                        String filter = entry.getKey();
                        groupedPredicate[i] = getPredicate(operation, filter, root, builder);
                    }
                    return builder.or(groupedPredicate);
                })
                .toArray(Predicate[]::new);

            Predicate[] fullPredicates = ArrayUtils.addAll(predicates, groupedPredicates);
            return builder.and(fullPredicates);
        };
    }

    private static <E> Predicate getPredicate(FilterOperation operation, String field, Root<E> root, CriteriaBuilder builder) {
        List<?> values = operation.getValues();
        return switch (operation.getType()) {
            case IN -> getPath(root, builder, field).in(values);
            case EQUALS -> builder.equal(
                getPath(root, builder, field),
                values.stream().findFirst().orElseThrow()
            );
            case NOT_IN -> getPath(root, builder, field).in(values).not();
            case LIKE -> {
                Object value = values.stream().findFirst().orElseThrow();
                String pattern = "%" + StringUtils.lowerCase(value.toString()) + "%";

                Path<String> path = getPath(root, builder, field);
                yield builder.like(builder.lower(path), pattern);
            }
            case NULL -> getPath(root, builder, field).isNull();
            case JSON_CONTAINS -> new JsonContainsPredicate<E>(
                builder,
                getPath(root, builder, field),
                builder.literal(getPreparedJsonValues(values, operation.getFilterValueType())),
                PostgreSQLPromoDialect.FUNCTION_JSONB_SEARCH
            );
            case JSON_STRING_OR -> new JsonContainsPredicate<E>(
                builder,
                getPath(root, builder, field),
                builder.literal(getPreparedJsonStringValues(values, operation.getFilterValueType())),
                PostgreSQLPromoDialect.FUNCTION_JSONB_SEARCH_STRING
            );
            case GREATER_OR_EQUAL -> builder.greaterThanOrEqualTo(
                getPath(root, builder, field),
                (Date) values.stream().findFirst().orElseThrow()
            );
            case LESS_OR_EQUAL -> builder.lessThanOrEqualTo(
                getPath(root, builder, field),
                (Date) values.stream().findFirst().orElseThrow()
            );
            case BETWEEN -> {
                Date firstDate = (Date) values.get(0);
                Date secondDate = (Date) values.get(1);

                if (firstDate != null && secondDate != null) {
                    yield builder.between(
                        getPath(root, builder, field),
                        (Date) values.get(0),
                        (Date) values.get(1)
                    );
                }
                if (firstDate == null && secondDate != null) {
                    yield builder.lessThanOrEqualTo(
                        getPath(root, builder, field),
                        secondDate
                    );
                }
                if (firstDate != null) {
                    yield builder.greaterThanOrEqualTo(
                        getPath(root, builder, field),
                        firstDate
                    );
                }

                yield null;
            }
        };
    }

    private static String getPreparedJsonValues(List<?> values, FilterValueType filterValueType) {
        StringBuilder stringBuilder = new StringBuilder(JSON_ARRAY_START_TOKEN);

        switch (filterValueType) {
            case LONG -> {
                for (Object value : values) {
                    stringBuilder.append(value);
                }
            }
            default -> throw new IllegalStateException(
                "Фильтрация по полю json с типом %s не реализована".formatted(filterValueType)
            );
        }
        stringBuilder.append(JSON_ARRAY_END_TOKEN);
        return stringBuilder.toString();
    }

    private static String getPreparedJsonStringValues(List<?> values, FilterValueType filterValueType) {
        return switch (filterValueType) {
            case STRING -> {
                String value = values.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
                yield "'%s'".formatted(value);
            }
            default -> throw new IllegalStateException(
                "Фильтрация по полю json с типом %s не реализована".formatted(filterValueType)
            );
        };
    }

    public static <T> Path<T> getPath(Root<?> root, CriteriaBuilder builder, String fieldName) {
        String[] compositeField = StringUtils.split(fieldName, ".");
        if (compositeField.length == 1) {
            return root.get(fieldName);
        }

        String field = compositeField[0];
        Path<T> path = root.get(field);

        for (int i = 1; i < compositeField.length; i++) {
            path = path.get(compositeField[i]);
        }

        return path;
    }

}
