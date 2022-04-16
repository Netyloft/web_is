package com.example.api.configuration.swagger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class OperationMultipleCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter parameter : methodParameters) {
            modifyRequestBody(operation, parameter);
        }
        return operation;
    }

    private void modifyRequestBody(Operation operation, MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        JsonSubTypes jsonSubTypes = parameterType.getDeclaredAnnotation(JsonSubTypes.class);
        if (jsonSubTypes == null) {
            return;
        }

        Map<String, Example> examples = new LinkedHashMap<>();
        for (JsonSubTypes.Type type : jsonSubTypes.value()) {
            Class<?> subType = type.value();
            Object instance = getInstance(subType);
            if (instance != null) {
                examples.put(type.name(), new Example().value(subType));
            }
        }

        MediaType mediaType = new MediaType();
        mediaType.setExamples(examples);

        Content content = new Content().addMediaType(APPLICATION_JSON_VALUE, mediaType);

        RequestBody requestBody = operation.getRequestBody();
        requestBody.setContent(content);
    }

    private Object getInstance(Class<?> subType) {
        Object instance = null;
        Constructor<?>[] constructors = subType.getDeclaredConstructors();
        if (constructors.length > 0) {
            Object[] vars = new Object[0];
            try {
                instance = constructors[0].newInstance(vars);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
