package com.example.api.configuration;

import com.example.api.resolver.filter.FilterResolver;
import com.example.api.resolver.paging.PagingResolver;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final List<Converter<?, ?>> converters; //todo не для всех приложений может понадобится, подумать на переоределением конфигурации

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(converter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PagingResolver());
        argumentResolvers.add(new FilterResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        converters.forEach(formatterRegistry::addConverter);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);

        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }

    @Bean
    @Primary
    public ObjectMapper provideObjectMapper() {
        return getObjectMapper();
    }

    @Bean(name = "snakeCaseObjectMapper")
    public ObjectMapper provideSnakeCaseMapper() {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return objectMapper;
    }

}
