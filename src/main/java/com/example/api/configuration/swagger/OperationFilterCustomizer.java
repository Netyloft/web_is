package com.example.api.configuration.swagger;

import com.example.api.resolver.filter.Filter;
import com.example.api.resolver.filter.FilterParameters;
import com.example.api.resolver.filter.FilterSource;
import com.example.api.resolver.filter.Filters;
import com.example.api.resolver.paging.Paging;
import com.example.api.resolver.paging.PagingParameters;
import com.example.api.resolver.paging.SortField;
import com.example.api.resolver.paging.Sorting;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class OperationFilterCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        if (CollectionUtils.isEmpty(operation.getParameters())) {
            operation.setParameters(new ArrayList<>());
        }
        List<Parameter> parameters = operation.getParameters();
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter parameter : methodParameters) {
            modifyRequestParameters(parameters, parameter);
        }
        operation.parameters(parameters);
        Operation resultOperation = removeRequestBodyFilterParam(operation);
        return resultOperation;
    }

    private void modifyRequestParameters(List<Parameter> parameters, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Filters) {
                addFiltersParameters((Filters) annotation, parameters);
            }
            if (annotation instanceof Paging) {
                addPagingParameters((Paging) annotation, parameters);
            }
        }
    }

    private void addPagingParameters(Paging paging, List<Parameter> parameters) {
        parameters.removeIf(param -> param.getSchema() != null && param.getSchema().get$ref() != null &&
            param.getSchema().get$ref().contains(PagingParameters.class.getSimpleName()));

        Parameter limitParameter = new QueryParameter()
            .name(paging.limitParam())
            .example(paging.limitDefault())
            .schema(new IntegerSchema())
            .description(String.format("Количество элементов в странице пагинации (интервал: %d - %d, по умолчанию: %d)",
                paging.limitMin(), paging.limitMax(), Paging.LIMIT_DEFAULT_VALUE))
            .required(false);

        Parameter offsetParameter = new QueryParameter()
            .name(paging.offsetParam())
            .example(paging.offsetDefault())
            .schema(new IntegerSchema())
            .description(String.format("Индекс первого элемента в странице пагинации (по умолчанию: %d)", Paging.OFFSET_DEFAULT_VALUE))
            .required(false);

        addSortingParameters(paging.sorting(), parameters);

        parameters.add(limitParameter);
        parameters.add(offsetParameter);
    }

    private void addSortingParameters(Sorting sorting, List<Parameter> parameters) {
        String description = "Имя поля сущности по которому будет производиться сортировка";
        SortField[] fields = sorting.availableFields();
        if (fields.length > 0) {
            String availableFields = Arrays.stream(fields)
                .map(SortField::name)
                .collect(Collectors.joining(", "));
            description = description + ". Доступные поля для сортировки: " + availableFields;
        }

        Parameter fieldParameter = new QueryParameter()
            .name(sorting.nameParam())
            .example(sorting.defaultName())
            .schema(new StringSchema())
            .description(description)
            .required(true);

        Parameter directionParameter = new QueryParameter()
            .name(sorting.directionParam())
            .example(sorting.defaultDirection())
            .schema(new StringSchema())
            .description(String.format("Порядок сортировки (по умолчанию: %s)", sorting.defaultDirection()))
            .required(false);

        parameters.add(fieldParameter);
        parameters.add(directionParameter);
    }

    private void addFiltersParameters(Filters filters, List<Parameter> parameters) {
        if (!CollectionUtils.isEmpty(parameters)) {
            parameters.removeIf(param -> param.getSchema() != null && param.getSchema().get$ref() != null &&
                param.getSchema().get$ref().contains(FilterParameters.class.getSimpleName()));
        }

        Map<Integer, String> conditionGroupDescription = prepareConditionGroupDescription(filters.value());

        for (Filter filter : filters.value()) {
            Parameter filterParameter = getFilterParameter(filter.source())
                .name(filter.name())
                .example(getExample(filter))
                .schema(getFilterSchema(filter))
                .required(filter.required());

            String description = prepareDescription(conditionGroupDescription, filter);
            if (StringUtils.isNotBlank(description)) {
                filterParameter = filterParameter.description(description);
            }

            parameters.add(filterParameter);
        }
    }

    private String prepareDescription(Map<Integer, String> conditionGroupDescription, Filter filter) {

        String conditionDescription = filter.isConditionGroup()
            ? conditionGroupDescription.get(filter.conditionGroupId())
            : null;

        String description = filter.description();

        return StringUtils.join(description, conditionDescription);
    }

    private Map<Integer, String> prepareConditionGroupDescription(Filter[] filters) {
        return filters == null
            ? new HashMap<>()
            : Arrays
                .stream(filters)
                .filter(Filter::isConditionGroup)
                .collect(Collectors.groupingBy(Filter::conditionGroupId))
                .entrySet()
                .stream()
                .map(entry -> {
                    Integer conditionGroupId = entry.getKey();
                    StringJoiner conditionGroupDescriptionJoiner = new StringJoiner(
                        ",",
                        "Поля: ",
                        " фильтруются через логическое ИЛИ. С другими полями эта группа фильтруется через И"
                    );
                    for (Filter filter : entry.getValue()) {
                        conditionGroupDescriptionJoiner.add(filter.field());
                    }
                    String additionalDescription = conditionGroupDescriptionJoiner.toString();
                    return Pair.of(conditionGroupId, additionalDescription);
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    }

    @SuppressWarnings("rawtypes, unchecked")
    private String getExample(Filter filter) {
        return switch (filter.type()) {
            case LONG, STRING, BOOLEAN, DATE -> filter.example();
            case ENUM -> { //todo поправить формирование примера для enum
                if (filter.enumerate().length != 1) {
                    yield String.format("Incorrect example for %s", filter.property());
                }
                Class<? extends Enum> enumClass = Arrays.stream(filter.enumerate()).findFirst().get();
                yield EnumUtils.getEnumMap(enumClass).keySet().stream()
                    .map(key -> key.toString().toLowerCase())
                    .collect(Collectors.joining("|"))
                    .toString();
            }
        };
    }

    private Parameter getFilterParameter(FilterSource source) {
        return switch (source) {
            case QUERY -> new QueryParameter();
            case PATH -> new PathParameter();
        };
    }

    private Schema<?> getFilterSchema(Filter filter) {
        return switch (filter.type()) {
            case LONG -> new IntegerSchema();
            case BOOLEAN -> new BooleanSchema();
            case STRING, ENUM, DATE -> new StringSchema();
        };
    }

    @SuppressWarnings("rawtypes")
    private Operation removeRequestBodyFilterParam(Operation operation) {
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody == null) {
            return operation;
        }
        Content content = requestBody.getContent();

        if (content == null) {
            return operation;
        }
        String jsonMediaType = MediaType.APPLICATION_JSON.toString();
        var mediaType = content.get(jsonMediaType);
        Schema schema = mediaType.getSchema();
        if ("object".equals(schema.getType())) {
            schema
                .getProperties()
                .remove("filters");

            Optional<Schema> schemaOptional = schema.getProperties().values().stream().findFirst();
            schemaOptional.ifPresent(mediaType::setSchema);

        } else if ("#/components/schemas/FilterParameters".equals(schema.get$ref())) {
            operation.setRequestBody(null);
        }
        return operation;
    }

}
