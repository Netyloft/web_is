package com.example.api.resolver.filter;

import com.example.exception.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@NoArgsConstructor
public class FilterParameters {

    private Map<String, FilterOperation> filters = new LinkedHashMap<>();

    public <T> List<T> getValues(String field, Class<T> type) {
        FilterOperation operation = filters.get(field);
        if (operation == null) {
            throw new NotFoundException("Фильтр не содержит операции для заданного параметра %s", field);
        }

        return operation.getValues().stream()
            .map(type::cast)
            .collect(Collectors.toList());
    }

    public <T> T getValue(String field, Class<T> type) {
        return type.cast(getValues(field, type).stream()
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Фильтр не содержит значений для заданного параметра %s", field)));
    }

    public <T> T getValue(String field, Class<T> enumType, String enumField) {
        Object filterValue = getValue(field, Object.class);
        return Arrays.stream(enumType.getEnumConstants())
            .filter(item -> {
                BeanWrapperImpl beanWrapper = new BeanWrapperImpl(item);
                return filterValue.equals(beanWrapper.getPropertyValue(enumField));
            })
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Фильтр не содержит значений для заданного параметра %s", field));
    }

    public void add(String field, FilterOperation operation) {
        filters.put(field, operation);
    }

    public void add(String field, Object value) {
        add(field, List.of(value));
    }

    public void add(String field, Object value, boolean manual) {
        add(field, List.of(value), manual);
    }

    public void add(String field, List<?> values, FilterOperatorType operationType) {
        add(field, values, operationType, false);
    }

    public void add(String field, List<?> values) {
        add(field, values, FilterOperatorType.IN, false, FilterValueType.STRING, null);
    }

    public void add(String field, List<?> values, boolean manual) {
        add(field, values, FilterOperatorType.IN, manual);
    }

    public void add(String field, Object value, FilterOperatorType operationType, boolean manual) {
        add(field, List.of(value), operationType, manual, FilterValueType.STRING, null);
    }

    public void add(String field, Object value, FilterOperatorType operationType) {
        add(field, List.of(value), operationType, false);
    }

    public void add(String field, List<?> values, FilterOperatorType operationType,
                    boolean manual, FilterValueType filterValueType, Integer groupNumber) {
        FilterOperation operation = new FilterOperation();
        operation.setType(operationType);
        operation.setValues(values);
        operation.setManual(manual);
        operation.setFilterValueType(filterValueType);
        operation.setGroupId(groupNumber);

        add(field, operation);
    }

    public boolean isExist(String field) {
       FilterOperation operation = filters.get(field);
        return operation != null && !isEmpty(operation.getValues());
    }

    @Nullable
    public FilterOperation getOperation(String field) {
        return filters.get(field);
    }

    public void remove(String field) {
        filters.remove(field);
    }

}

