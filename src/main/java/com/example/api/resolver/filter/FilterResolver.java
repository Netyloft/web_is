package com.example.api.resolver.filter;

import com.example.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;


@Component
@Slf4j
public class FilterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Filters.class) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) {
        Filters filters = requireNonNull(parameter.getParameterAnnotation(Filters.class));

        Map<String, String> pathVariableMap = (Map<String, String>) request.getAttribute(
            HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        Map<String, String[]> queryParameterMap = request.getParameterMap();

        FilterParameters filterParameters = new FilterParameters();
        for (Filter filter : filters.value()) {
            List<Object> filterValues = switch (filter.source()) {
                case PATH -> getPathFilterValues(filter, requireNonNull(pathVariableMap));
                case QUERY -> getParamFilterValues(filter, queryParameterMap);
            };

            if (filterValues != null) {
                Integer conditionGroup = filter.isConditionGroup()
                    ? filter.conditionGroupId()
                    : null;

                filterParameters.add(
                    filter.field(),
                    filterValues,
                    filter.operation(),
                    filter.manual(),
                    filter.type(),
                    conditionGroup
                );
            }
        }
        return filterParameters;
    }

    private List<Object> getPathFilterValues(Filter filter, Map<String, String> pathVariableMap) {
        String value = pathVariableMap.get(filter.name());
        return switch (filter.type()) {
            case LONG -> List.of(Long.parseLong(value));
            case STRING, BOOLEAN, DATE -> List.of(value);
            case ENUM -> getFilterValuesFromEnum(filter, Collections.singletonList(value));
        };
    }

    private List<Object> getParamFilterValues(Filter filter, Map<String, String[]> queryParameterMap) {
        String[] paramValues = queryParameterMap.get(filter.name());
        if (paramValues == null || paramValues.length == 0) {
            if (filter.required()) {
                throw new BadRequestException("Не задан обязательный параметр %s", filter.name());
            } else {
                return null;
            }
        }
        List<String> values = Arrays.stream(paramValues).toList();
        return switch (filter.type()) {
            case LONG, STRING, BOOLEAN, DATE -> getFilterValuesFromList(filter, values);
            case ENUM -> getFilterValuesFromEnum(filter, values);
        };
    }

    private List<Object> getFilterValuesFromList(Filter filter, List<String> paramValues) {
        List<Object> filterValues = new ArrayList<>();
        for (String paramValue : paramValues) {
            String[] splitValues = paramValue.split(",", -1);

            Stream<String> stream = Arrays.stream(splitValues);

            Stream<Object> mapSteam;
            if (filter.type() == FilterValueType.LONG) {
                mapSteam = stream.map(value -> Long.parseLong(value.trim()));
            } else if (filter.type() == FilterValueType.STRING) {
                mapSteam = stream.map(String::trim);
            } else if (filter.type() == FilterValueType.BOOLEAN) {
                mapSteam = stream.map(value -> Boolean.parseBoolean(value.trim()));
            } else if (filter.type() == FilterValueType.DATE) {
                if (filter.operation() == FilterOperatorType.BETWEEN) {
                    List<String> values = stream.toList();

                    String firstDate = values.get(0);
                    String secondDate = values.get(1);

                    if (!firstDate.isEmpty()) {
                        filterValues.add(addMinTime(firstDate));
                    } else {
                        filterValues.add(null);
                    }

                    if (!secondDate.isEmpty()) {
                        filterValues.add(addMaxTime(secondDate));
                    } else {
                        filterValues.add(null);
                    }

                    return filterValues;
                }
                mapSteam = stream.map(value -> convertStringToDate(value, filter.operation()));
            } else {
                throw new BadRequestException("Неподдерживаемый тип %s параметра фильтрации", filter.type().name());
            }

            List<Object> values = mapSteam.collect(Collectors.toList());
            filterValues.addAll(values);
        }
        return filterValues;
    }

    private Date convertStringToDate(String date, FilterOperatorType type) {
        if (type == FilterOperatorType.LESS_OR_EQUAL) {
            return addMaxTime(date);
        }
        return addMinTime(date);
    }

    private Date addMinTime(String date) {
        return Date.from(ZonedDateTime.parse(date).toLocalDateTime().with(LocalTime.MIN).toInstant(ZoneOffset.UTC));
    }

    private Date addMaxTime(String date) {
        return Date.from(ZonedDateTime.parse(date).toLocalDateTime().with(LocalTime.MAX).toInstant(ZoneOffset.UTC));
    }

    @SuppressWarnings("rawtypes, unchecked")
    private List<Object> getFilterValuesFromEnum(Filter filter, List<String> paramValues) {
        List<Object> values = new ArrayList<>();
        if (filter.enumerate().length != 1) {
            throw new BadRequestException("Некорректные настройки вида перечисления для параметра %s", filter.name());
        }

        Class<? extends Enum> enumClass = Arrays.stream(filter.enumerate()).findFirst().get();
        for (String paramValue : paramValues) {
            String[] splitValues = StringUtils.split(paramValue, ",");
            for (String value : splitValues) {
                Enum<?> enumValue = EnumUtils.getEnumIgnoreCase(enumClass, value);
                if (enumValue == null) {
                    throw new BadRequestException("Неизвестное значение %s для перечисления %s", value, enumClass.getSimpleName());
                }
                String property = filter.property();
                if (StringUtils.isNotEmpty(property)) {
                    final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(enumValue);
                    Object propertyValue = beanWrapper.getPropertyValue(property);
                    values.add(propertyValue);
                } else {
                    values.add(enumValue);
                }
            }
        }
        return values;
    }

}
