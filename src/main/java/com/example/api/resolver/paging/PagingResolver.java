package com.example.api.resolver.paging;

import com.example.exception.BadRequestException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Component
public class PagingResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Paging.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) {
        Paging paging = requireNonNull(parameter.getParameterAnnotation(Paging.class));

        Map<String, String[]> maps = request.getParameterMap();
        return PagingParameters.builder()
            .limit(getLimitValue(paging, maps))
            .offset(getOffsetValue(paging, maps))
            .fields(getSortValues(paging.sorting(), maps))
            .build();
    }

    private int getLimitValue(Paging paging, Map<String, String[]> maps) {
        String param = paging.limitParam();
        String[] values = maps.get(param);
        if (values != null && values.length > 0) {
            String value = values[0];
            int limitMin = paging.limitMin();
            int limitMax = paging.limitMax();
            int limitValue = Integer.parseInt(value);
            if (limitValue >= limitMin && limitValue <= limitMax) {
                return limitValue;
            } else {
                String message = format("Query parameter %s value must be between %d and %d", param, limitMin, limitMax);
                throw new BadRequestException(message);
            }
        } else {
            return paging.limitDefault();
        }
    }

    private int getOffsetValue(Paging paging, Map<String, String[]> maps) {
        String param = paging.offsetParam();
        String[] values = maps.get(param);
        if (values != null && values.length > 0) {
            String value = values[0];
            return Integer.parseInt(value);
        } else {
            int offsetDefault = paging.offsetDefault();
            if (offsetDefault >= 0) {
                return offsetDefault;
            } else {
                throw new BadRequestException(format("Query parameter %s default value must be set", param));
            }
        }
    }

    private Map<String, String> getSortValues(Sorting sorting, Map<String, String[]> maps) {
        return Collections.singletonMap(
            getSortFieldName(sorting, maps),
            getSortDirectionName(sorting, maps)
        );
    }

    private String getSortFieldName(Sorting sorting, Map<String, String[]> maps) {
        String sortFieldParam = sorting.nameParam();
        String[] sortFieldParams = maps.get(sortFieldParam);

        String sortFieldName;
        if (sortFieldParams != null && sortFieldParams.length > 0) {
            sortFieldName = sortFieldParams[0];
        } else {
            sortFieldName = sorting.defaultName();
        }

        SortField[] fields = sorting.availableFields();
        if (fields.length > 0) {
            SortField sortField = Arrays.stream(fields)
                .filter(item -> item.name().equals(sortFieldName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Сортировка по имени параметра %s не доступна", sortFieldName));

            return sortField.field();
        } else {
            return sortFieldName;
        }
    }

    private String getSortDirectionName(Sorting sorting, Map<String, String[]> maps) {
        String sortDirectionParam = sorting.directionParam();
        String[] sortDirectionParams = maps.get(sortDirectionParam);

        if (sortDirectionParams != null && sortDirectionParams.length > 0) {
            return sortDirectionParams[0];
        } else {
            return sorting.defaultDirection();
        }
    }

}
